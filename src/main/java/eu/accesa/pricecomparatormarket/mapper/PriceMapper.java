package eu.accesa.pricecomparatormarket.mapper;

import eu.accesa.pricecomparatormarket.dtos.PriceHistoryResponseDto;
import eu.accesa.pricecomparatormarket.models.price.Price;

public class PriceMapper {
    public static PriceHistoryResponseDto extractPriceHistoryResponseDtoFromPrice(Price price) {
        PriceHistoryResponseDto priceHistoryResponseDto = new PriceHistoryResponseDto();
        priceHistoryResponseDto.setPrice(price.getPrice());
        priceHistoryResponseDto.setStoreName(price.getStore().getName());
        priceHistoryResponseDto.setStartDate(price.getEffectiveDate());
        return priceHistoryResponseDto;
    }
}
