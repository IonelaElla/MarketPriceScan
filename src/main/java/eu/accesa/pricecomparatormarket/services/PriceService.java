package eu.accesa.pricecomparatormarket.services;

import eu.accesa.pricecomparatormarket.dtos.PriceHistoryResponseDto;
import eu.accesa.pricecomparatormarket.dtos.ProductPriceHistoryResponseDto;
import eu.accesa.pricecomparatormarket.mapper.PriceMapper;
import eu.accesa.pricecomparatormarket.models.price.Price;
import eu.accesa.pricecomparatormarket.models.price.PriceRepository;
import eu.accesa.pricecomparatormarket.models.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PriceService {

    private PriceRepository priceRepository;

    @Autowired
    public void setPriceRepository(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public List<ProductPriceHistoryResponseDto> getAllProductPrices(String storeName, String productCategory, String productBrand) {
        List<Price> productPrices = priceRepository.filterBy(storeName, productCategory, productBrand);
        List<String> productNames = productPrices.stream().map((productPrice) -> productPrice.getProduct().getProductName()).toList();
        List<ProductPriceHistoryResponseDto> productPriceHistoryResponseDtoList = new ArrayList<>();
        for(String productName: productNames) {
            List<Price> productPriceList = productPrices.stream().filter((productPrice) -> Objects.equals(productPrice.getProduct().getProductName(), productName)).toList();
            List<PriceHistoryResponseDto> priceHistoryResponseDtoList = productPriceList.stream().map((PriceMapper::extractPriceHistoryResponseDtoFromPrice)).toList();

            ProductPriceHistoryResponseDto productPriceHistoryResponseDto = getProductPriceHistoryResponseDto(productPriceList, priceHistoryResponseDtoList);

            productPriceHistoryResponseDtoList.add(productPriceHistoryResponseDto);
        }
        return productPriceHistoryResponseDtoList;
    }

    private ProductPriceHistoryResponseDto getProductPriceHistoryResponseDto(List<Price> productPriceList, List<PriceHistoryResponseDto> priceHistoryResponseDtoList) {
        Product product = productPriceList.get(0).getProduct();

        ProductPriceHistoryResponseDto productPriceHistoryResponseDto = new ProductPriceHistoryResponseDto();

        productPriceHistoryResponseDto.setPriceHistoryResponseDtoList(priceHistoryResponseDtoList);

        productPriceHistoryResponseDto.setProductId(product.getProductId());
        productPriceHistoryResponseDto.setProductName(product.getProductName());
        productPriceHistoryResponseDto.setProductCategory(product.getProductCategory());
        productPriceHistoryResponseDto.setBrand(product.getBrand());
        productPriceHistoryResponseDto.setPackageUnit(product.getPackageUnit());
        productPriceHistoryResponseDto.setPackageQuantity(product.getPackageQuantity());

        return productPriceHistoryResponseDto;
    }
}
