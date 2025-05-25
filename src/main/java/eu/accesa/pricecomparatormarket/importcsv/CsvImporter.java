package eu.accesa.pricecomparatormarket.importcsv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class CsvImporter {
    private ProductsDataImporter productsDataImporter;

    @Value("${application.import.csv.dir}")
    private String csvDirectory;

    @Value("${application.import.csv.separator}")
    private Character csvSeparator;

    @Autowired
    public void setProductsDataImporter(ProductsDataImporter productsDataImporter) {
        this.productsDataImporter = productsDataImporter;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        try {
            Path csvDirectoryPath = Paths.get(csvDirectory);
            productsDataImporter.importPricesFromCsvFiles(csvDirectoryPath, csvSeparator);
        } catch (Exception e) {
            System.err.println("Failed to import CSV: " + e.getMessage());
        }
    }
}
