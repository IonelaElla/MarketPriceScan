package eu.accesa.pricecomparatormarket.dtos;

import java.math.BigDecimal;
import java.util.List;

public class ProductPriceHistoryResponseDto {
    private String productId;
    private String productName;
    private String productCategory;
    private String brand;
    private BigDecimal packageQuantity;
    private String packageUnit;

    private List<PriceHistoryResponseDto> priceHistoryResponseDtoList;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(BigDecimal packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public String getPackageUnit() {
        return packageUnit;
    }

    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit;
    }

    public List<PriceHistoryResponseDto> getPriceHistoryResponseDtoList() {
        return priceHistoryResponseDtoList;
    }

    public void setPriceHistoryResponseDtoList(List<PriceHistoryResponseDto> priceHistoryResponseDtoList) {
        this.priceHistoryResponseDtoList = priceHistoryResponseDtoList;
    }
}
