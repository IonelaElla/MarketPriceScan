package eu.accesa.pricecomparatormarket.controllers;

import eu.accesa.pricecomparatormarket.dtos.DiscountListResponseDto;
import eu.accesa.pricecomparatormarket.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(DiscountController.BASE_URL)
public class DiscountController {

    protected static final String BASE_URL = "api/discounts";

    private final ProductService productService;

    public DiscountController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/top")
    @ResponseStatus(HttpStatus.OK)
    public DiscountListResponseDto getTopAvailableDiscounts(@RequestParam(name = "limit") Integer limit) {
        return new DiscountListResponseDto(productService.getTopDiscounts(limit));
    }

    @GetMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public DiscountListResponseDto getTodayNewDiscounts() {
        return new DiscountListResponseDto(productService.getTodayNewDiscounts());
    }
}
