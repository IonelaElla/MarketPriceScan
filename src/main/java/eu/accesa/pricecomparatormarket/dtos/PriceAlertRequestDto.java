package eu.accesa.pricecomparatormarket.dtos;

import java.math.BigDecimal;

public class PriceAlertRequestDto {
   private String productId;
   private String userEmail;
   private BigDecimal targetPrice;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }
}
