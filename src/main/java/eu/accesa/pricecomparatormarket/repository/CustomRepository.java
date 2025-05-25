package eu.accesa.pricecomparatormarket.repository;

import eu.accesa.pricecomparatormarket.dtos.ProductDetailsDto;
import eu.accesa.pricecomparatormarket.dtos.ProductSubstituteDto;

import java.time.LocalDate;
import java.util.List;

public interface CustomRepository {
    List<ProductDetailsDto> findProductPricesWithDiscounts(List<String> productNames);

    List<ProductDetailsDto> findTopAvailableDiscounts(Integer limit);

    List<ProductDetailsDto> findDiscountsStartingBy(LocalDate startDate);

    List<ProductSubstituteDto> findProductSubstitutes(String productName, String productCategory);


}
