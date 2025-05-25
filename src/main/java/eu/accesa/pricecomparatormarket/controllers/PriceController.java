package eu.accesa.pricecomparatormarket.controllers;

import eu.accesa.pricecomparatormarket.dtos.ProductPriceHistoryListResponseDto;
import eu.accesa.pricecomparatormarket.services.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PriceController.BASE_URL)
public class PriceController {
    protected static final String BASE_URL = "api/prices";

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    public ProductPriceHistoryListResponseDto getPricesHistory(@RequestParam(name = "storeName", required = false) String storeName,
                                                               @RequestParam(name = "productCategory", required = false) String productCategory,
                                                               @RequestParam(name = "productBrand", required = false) String productBrand) {
        return new ProductPriceHistoryListResponseDto(priceService.getAllProductPrices(storeName, productCategory, productBrand));
    }

}
