package eu.accesa.pricecomparatormarket.dtos;

import java.util.List;

public class ShoppingBasketResponseDto {

    private List<ShoppingBasketListResponseDto> comparedLists;
    private RecommendedShoppingBasketResponseDto recommendedShoppingBasketResponseDto;

    public List<ShoppingBasketListResponseDto> getComparedLists() {
        return comparedLists;
    }

    public void setComparedLists(List<ShoppingBasketListResponseDto> comparedLists) {
        this.comparedLists = comparedLists;
    }

    public RecommendedShoppingBasketResponseDto getRecommendedShoppingBasketResponseDto() {
        return recommendedShoppingBasketResponseDto;
    }

    public void setRecommendedShoppingBasketResponseDto(RecommendedShoppingBasketResponseDto recommendedShoppingBasketResponseDto) {
        this.recommendedShoppingBasketResponseDto = recommendedShoppingBasketResponseDto;
    }
}
