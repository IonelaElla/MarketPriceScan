package eu.accesa.pricecomparatormarket.dtos;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingBasketListResponseDto {
    private String storeName;
    private List<ProductResponseDto> products;

    private BigDecimal totalCost;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<ProductResponseDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDto> products) {
        this.products = products;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
