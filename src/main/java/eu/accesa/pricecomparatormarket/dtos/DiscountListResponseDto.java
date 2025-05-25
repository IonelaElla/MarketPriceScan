package eu.accesa.pricecomparatormarket.dtos;

import java.util.List;

public class DiscountListResponseDto {
    private final List<ProductDetailsDto> discounts;

    public DiscountListResponseDto(List<ProductDetailsDto> discounts) {
        this.discounts = discounts;
    }

    public List<ProductDetailsDto> getDiscounts() {
        return discounts;
    }
}
