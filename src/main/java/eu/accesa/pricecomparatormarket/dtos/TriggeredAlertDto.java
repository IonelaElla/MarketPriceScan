package eu.accesa.pricecomparatormarket.dtos;

import java.math.BigDecimal;

public class TriggeredAlertDto {

    private String productId;
    private String productName;
    private BigDecimal targetPrice;
    private BigDecimal currentPrice;
    private boolean triggered;

    public TriggeredAlertDto() {
    }

    public TriggeredAlertDto(String productId, String productName, BigDecimal targetPrice, BigDecimal currentPrice, boolean triggered) {
        this.productId = productId;
        this.productName = productName;
        this.targetPrice = targetPrice;
        this.currentPrice = currentPrice;
        this.triggered = triggered;
    }

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

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    @Override
    public String toString() {
        return "TriggeredAlertDto{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", targetPrice=" + targetPrice +
                ", currentPrice=" + currentPrice +
                ", triggered=" + triggered +
                '}';
    }
}
