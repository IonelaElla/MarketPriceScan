package eu.accesa.pricecomparatormarket.importcsv;

import java.time.LocalDate;

public class CsvFileDetails {
    private final CsvFileType csvFileType;
    private final String storeName;
    private final LocalDate date;

    public CsvFileDetails(CsvFileType csvFileType, String storeName, LocalDate date) {
        this.csvFileType = csvFileType;
        this.storeName = storeName;
        this.date = date;
    }

    public CsvFileType getCsvFileType() {
        return csvFileType;
    }

    public String getStoreName() {
        return storeName;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "CsvFileDetails{" +
                "csvFileType=" + csvFileType +
                ", storeName='" + storeName + '\'' +
                ", date=" + date +
                '}';
    }
}
