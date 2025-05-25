package eu.accesa.pricecomparatormarket.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceHistoryResponseDto {
    private BigDecimal price;
    private String storeName;
    private LocalDate startDate;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
