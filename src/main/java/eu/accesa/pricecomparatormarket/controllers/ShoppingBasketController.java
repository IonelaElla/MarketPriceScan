package eu.accesa.pricecomparatormarket.controllers;

import eu.accesa.pricecomparatormarket.dtos.ShoppingBasketListRequestDto;
import eu.accesa.pricecomparatormarket.dtos.ShoppingBasketResponseDto;
import eu.accesa.pricecomparatormarket.services.ShoppingBasketService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ShoppingBasketController.BASE_URL)
public class ShoppingBasketController {
    protected static final String BASE_URL = "api/basket";

    private final ShoppingBasketService shoppingBasketService;

    public ShoppingBasketController(ShoppingBasketService shoppingBasketService) {
        this.shoppingBasketService = shoppingBasketService;
    }

    @PostMapping("/optimize")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingBasketResponseDto optimizeShoppingBasket(@RequestBody ShoppingBasketListRequestDto shoppingBasketListRequestDto) {
        return shoppingBasketService.optimizeShoppingBasketByCheapestStores(shoppingBasketListRequestDto.getProductNames());
    }
}
