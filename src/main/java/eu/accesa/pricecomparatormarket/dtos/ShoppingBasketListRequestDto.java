package eu.accesa.pricecomparatormarket.dtos;

import java.util.List;

public class ShoppingBasketListRequestDto {
    private List<String> productNames;

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }
}
