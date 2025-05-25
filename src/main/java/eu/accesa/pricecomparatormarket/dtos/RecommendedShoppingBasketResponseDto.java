package eu.accesa.pricecomparatormarket.dtos;

import java.math.BigDecimal;
import java.util.List;

public class RecommendedShoppingBasketResponseDto {
    private List<ShoppingBasketListResponseDto> recommendedLists;
    private BigDecimal basketTotalCost;

    public List<ShoppingBasketListResponseDto> getRecommendedLists() {
        return recommendedLists;
    }

    public void setRecommendedLists(List<ShoppingBasketListResponseDto> recommendedLists) {
        this.recommendedLists = recommendedLists;
    }

    public BigDecimal getBasketTotalCost() {
        return basketTotalCost;
    }

    public void setBasketTotalCost(BigDecimal basketTotalCost) {
        this.basketTotalCost = basketTotalCost;
    }
}
