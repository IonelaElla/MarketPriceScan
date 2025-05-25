package eu.accesa.pricecomparatormarket.services;

import eu.accesa.pricecomparatormarket.dtos.ProductSubstituteDto;
import eu.accesa.pricecomparatormarket.repository.CustomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRecommendationService {

    private final CustomRepository customRepository;

    public ProductRecommendationService(CustomRepository customRepository) {
        this.customRepository = customRepository;
    }

    public List<ProductSubstituteDto> getSubstitutes(String productName, String productCategory) {
        return customRepository.findProductSubstitutes(productName, productCategory);
    }
}
