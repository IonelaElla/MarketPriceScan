package eu.accesa.pricecomparatormarket.dtos;

import java.util.List;

public class ProductPriceHistoryListResponseDto {

    private final List<ProductPriceHistoryResponseDto> productPriceHistoryResponseDtoList;

    public ProductPriceHistoryListResponseDto(List<ProductPriceHistoryResponseDto> productPriceHistoryResponseDtoList) {
        this.productPriceHistoryResponseDtoList = productPriceHistoryResponseDtoList;
    }

    public List<ProductPriceHistoryResponseDto> getProductPriceHistoryResponseDtoList() {
        return productPriceHistoryResponseDtoList;
    }
}
