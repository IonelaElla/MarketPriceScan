package eu.accesa.pricecomparatormarket.controllers;

import eu.accesa.pricecomparatormarket.dtos.ProductSubstituteDto;
import eu.accesa.pricecomparatormarket.services.ProductRecommendationService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ProductRecommendationController.BASE_URL)
public class ProductRecommendationController {

    protected static final String BASE_URL = "api/recommendation";

    private final ProductRecommendationService recommendationService;

    public ProductRecommendationController(ProductRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductSubstituteDto> getProductRecommendations(
            @RequestParam(name = "productName" , required = false) String productName,
            @RequestParam(name= "productCategory", required = false) String productCategory
    ) {
        return recommendationService.getSubstitutes(productName, productCategory);
    }
}
