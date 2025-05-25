package eu.accesa.pricecomparatormarket.dtos;

import java.math.BigDecimal;

public class ProductSubstituteDto {

    private String productId;
    private String productName;
    private String brand;
    private String productCategory;
    private BigDecimal packageQuantity;
    private String packageUnit;
    private BigDecimal price;
    private BigDecimal valuePerUnit;

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrand() {
        return brand;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public BigDecimal getPackageQuantity() {
        return packageQuantity;
    }

    public String getPackageUnit() {
        return packageUnit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getValuePerUnit() {
        return valuePerUnit;
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public void setPackageQuantity(BigDecimal packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setValuePerUnit(BigDecimal valuePerUnit) {
        this.valuePerUnit = valuePerUnit;
    }
}
