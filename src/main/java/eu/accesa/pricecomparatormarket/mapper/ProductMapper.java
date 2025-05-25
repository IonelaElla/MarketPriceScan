package eu.accesa.pricecomparatormarket.mapper;

import eu.accesa.pricecomparatormarket.dtos.ProductResponseDto;
import eu.accesa.pricecomparatormarket.dtos.ProductDetailsDto;

public class ProductMapper {

    public static ProductResponseDto extractProductResponseDtoFromStoreProductPriceDto(ProductDetailsDto productDetailsDto) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setProductId(productDetailsDto.getProductId());
        productResponseDto.setProductName(productDetailsDto.getProductName());
        productResponseDto.setBrand(productDetailsDto.getBrand());
        productResponseDto.setProductCategory(productDetailsDto.getProductCategory());
        productResponseDto.setPackageUnit(productDetailsDto.getPackageUnit());
        productResponseDto.setPackageQuantity(productDetailsDto.getPackageQuantity());
        productResponseDto.setOriginalPrice(productDetailsDto.getOriginalPrice());
        productResponseDto.setPercentageOfDiscount(productDetailsDto.getDiscountResponseDto().getPercentageOfDiscount());
        productResponseDto.setFinalPrice(productDetailsDto.getFinalPrice());
        return productResponseDto;
    }
}
