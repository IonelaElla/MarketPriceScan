package eu.accesa.pricecomparatormarket.importcsv;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import eu.accesa.pricecomparatormarket.models.discount.Discount;
import eu.accesa.pricecomparatormarket.models.discount.DiscountRepository;
import eu.accesa.pricecomparatormarket.models.price.Price;
import eu.accesa.pricecomparatormarket.models.price.PriceRepository;
import eu.accesa.pricecomparatormarket.models.product.Product;
import eu.accesa.pricecomparatormarket.models.product.ProductRepository;
import eu.accesa.pricecomparatormarket.models.store.Store;
import eu.accesa.pricecomparatormarket.models.store.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsDataImporter {

    private ProductRepository productRepository;
    private StoreRepository storeRepository;
    private PriceRepository priceRepository;
    private DiscountRepository discountRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setStoreRepository(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Autowired
    public void setPriceRepository(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Autowired
    public void setDiscountRepository(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public void importPricesFromCsvFiles(Path csvDirectoryPath, char csvSeparator) throws IOException {
        List<Path> csvFilePaths = getCsvFilePaths(csvDirectoryPath);
        for (Path csvFilePath: csvFilePaths) {
            CsvFileDetails csvFileDetails = getCsvFileDetails(csvFilePath.getFileName().toString());

            try (CSVReader reader = new CSVReaderBuilder(new FileReader(csvFilePath.toFile()))
                        .withCSVParser(new CSVParserBuilder().withSeparator(csvSeparator).build())
                        .withSkipLines(1)
                        .build()) {
                String[] row;

                if (csvFileDetails.getCsvFileType() == CsvFileType.PRICES) {
                    while ((row = reader.readNext()) != null) {
                        importPriceCsvRow(row, csvFileDetails);
                    }
                }

                if (csvFileDetails.getCsvFileType() == CsvFileType.DISCOUNTS) {
                    while ((row = reader.readNext()) != null) {
                        importDiscountCsvRow(row, csvFileDetails);
                    }
                }

            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<Path> getCsvFilePaths(Path csvDir) throws IOException {
        List<Path> csvFilePaths = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(csvDir)) {
            for (Path filePath: stream) {
                csvFilePaths.add(filePath);
            }
        }
        return csvFilePaths;
    }

    private CsvFileDetails getCsvFileDetails(String csvFileName) {
        CsvFileType csvFileType = csvFileName.contains("discounts") ? CsvFileType.DISCOUNTS : CsvFileType.PRICES;
        String csvFileNameWithoutExtension = csvFileName.replaceFirst("\\.csv$", "");
        String[] csvFileNameParts = csvFileNameWithoutExtension.split("_");
        String storeName = csvFileNameParts[0];
        String dateAsString = csvFileNameParts[csvFileNameParts.length - 1];
        LocalDate date = LocalDate.parse(dateAsString);
        return new CsvFileDetails(csvFileType, storeName, date);
    }

    @Transactional
    private void importPriceCsvRow(String[] row, CsvFileDetails csvFileDetails) {
        try {
            String productId = row[0];
            String productName = row[1];
            String productCategory = row[2];
            String brand = row[3];
            BigDecimal packageQuantity = new BigDecimal(row[4]);
            String packageUnit = row[5];
            BigDecimal priceValue = new BigDecimal(row[6]);
            String currency = row[7];
            LocalDate effectiveDate = csvFileDetails.getDate();

            Product foundProduct = productRepository.findByProductId(productId).orElseGet(() -> {
                Product product = new Product();
                product.setProductId(productId);
                product.setProductName(productName);
                product.setProductCategory(productCategory);
                product.setBrand(brand);
                product.setPackageQuantity(packageQuantity);
                product.setPackageUnit(packageUnit);
                return productRepository.save(product);
            });


            Store foundStore = storeRepository.findByName(csvFileDetails.getStoreName()).orElseGet(() -> {
                Store store = new Store();
                store.setName(csvFileDetails.getStoreName());
                return storeRepository.save(store);
            });

            priceRepository.findBy(foundStore.getId(), foundProduct.getId(), effectiveDate).orElseGet(() -> {
                Price price = new Price();
                price.setProduct(foundProduct);
                price.setStore(foundStore);
                price.setPrice(priceValue);
                price.setCurrency(currency);
                price.setEffectiveDate(effectiveDate);
                return priceRepository.save(price);
            });
        } catch (Exception e) {
            System.err.println("[importPriceCsvRow] Failed to commit transaction: " + e.getMessage());
        }
    }

    @Transactional
    private void importDiscountCsvRow(String[] row, CsvFileDetails csvFileDetails) {
        try {
            String productId = row[0];
            String productName = row[1];
            String brand = row[2];
            BigDecimal packageQuantity = new BigDecimal(row[3]);
            String packageUnit = row[4];
            String productCategory = row[5];
            LocalDate fromDate = LocalDate.parse(row[6]);
            LocalDate toDate = LocalDate.parse(row[7]);
            Integer percentageOfDiscount = Integer.parseInt(row[8]);

            Product foundProduct = productRepository.findByProductId(productId).orElseGet(() -> {
                Product product = new Product();
                product.setProductId(productId);
                product.setProductName(productName);
                product.setProductCategory(productCategory);
                product.setBrand(brand);
                product.setPackageQuantity(packageQuantity);
                product.setPackageUnit(packageUnit);
                return productRepository.save(product);
            });


            Store foundStore = storeRepository.findByName(csvFileDetails.getStoreName()).orElseGet(() -> {
                Store store = new Store();
                store.setName(csvFileDetails.getStoreName());
                return storeRepository.save(store);
            });

            discountRepository.findBy(foundStore.getId(), foundProduct.getId(), fromDate, toDate).orElseGet(() -> {
                Discount discount = new Discount();
                discount.setFromDate(fromDate);
                discount.setToDate(toDate);
                discount.setPercentageOfDiscount(percentageOfDiscount);
                discount.setProduct(foundProduct);
                discount.setStore(foundStore);
                return discountRepository.save(discount);
            });
        } catch (Exception e) {
            System.err.println("[importDiscountCsvRow] Failed to commit transaction: " + e.getMessage());
        }
    }
}
