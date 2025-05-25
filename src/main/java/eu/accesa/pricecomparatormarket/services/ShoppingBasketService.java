package eu.accesa.pricecomparatormarket.services;

import eu.accesa.pricecomparatormarket.dtos.*;
import eu.accesa.pricecomparatormarket.mapper.ProductMapper;
import eu.accesa.pricecomparatormarket.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class ShoppingBasketService {

    private CustomRepository customRepository;

    @Autowired
    public void setCustomRepository(CustomRepository customRepository) {
        this.customRepository = customRepository;
    }

    public ShoppingBasketResponseDto optimizeShoppingBasketByCheapestStores(List<String> productNames) {
        ShoppingBasketResponseDto optimizedShoppingBasket = new ShoppingBasketResponseDto();

        List<ProductDetailsDto> results = customRepository.findProductPricesWithDiscounts(productNames);
        List<String> storeNames = results.stream().map(ProductDetailsDto::getStoreName).distinct().toList();

        List<ShoppingBasketListResponseDto> comparedShoppingLists = getComparedListsForShoppingBasket(results, storeNames);
        List<ShoppingBasketListResponseDto> recommendedShoppingLists = getRecommendedListsForShoppingBasket(results, productNames);

        optimizedShoppingBasket.setComparedLists(comparedShoppingLists);

        RecommendedShoppingBasketResponseDto recommendedShoppingBasketResponseDto = new RecommendedShoppingBasketResponseDto();
        recommendedShoppingBasketResponseDto.setRecommendedLists(recommendedShoppingLists);
        BigDecimal recommendedShoppingBasketTotalCost = recommendedShoppingLists.stream().map(ShoppingBasketListResponseDto::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        recommendedShoppingBasketResponseDto.setBasketTotalCost(recommendedShoppingBasketTotalCost);

        optimizedShoppingBasket.setRecommendedShoppingBasketResponseDto(recommendedShoppingBasketResponseDto);
        return optimizedShoppingBasket;
    }

    private List<ShoppingBasketListResponseDto> getComparedListsForShoppingBasket(List<ProductDetailsDto> results, List<String> storeNames) {
        List<ShoppingBasketListResponseDto> comparedShoppingLists = new ArrayList<>();
        for (String storeName: storeNames) {
            List<ProductDetailsDto> resultsPerStore = results.stream().filter((productDetailsDto -> Objects.equals(productDetailsDto.getStoreName(), storeName))).toList();
            ShoppingBasketListResponseDto shoppingBasketListResponseDto = new ShoppingBasketListResponseDto();
            shoppingBasketListResponseDto.setStoreName(storeName);
            List<ProductResponseDto> productsPerStore = resultsPerStore.stream().map(ProductMapper::extractProductResponseDtoFromStoreProductPriceDto).toList();
            BigDecimal totalCost = productsPerStore.stream().map(ProductResponseDto::getFinalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

            shoppingBasketListResponseDto.setProducts(productsPerStore);
            shoppingBasketListResponseDto.setTotalCost(totalCost);
            comparedShoppingLists.add(shoppingBasketListResponseDto);
        }
        return comparedShoppingLists;
    }

    private List<ShoppingBasketListResponseDto> getRecommendedListsForShoppingBasket(List<ProductDetailsDto> results, List<String> productNames) {
        List<ShoppingBasketListResponseDto> recommendedShoppingLists = new ArrayList<>();

        Map<String, List<ProductResponseDto>> cheapestStoreToProductsMap = new HashMap<>();
        for (String productName: productNames) {
            ProductDetailsDto cheapestStoreForCurrentProduct = results.stream().filter((result) -> Objects.equals(result.getProductName(), productName)).min(Comparator.comparing(ProductDetailsDto::getFinalPrice)).orElse(null);

            if (cheapestStoreForCurrentProduct == null) {
                continue;
            }

            List<ProductResponseDto> associatedProducts = cheapestStoreToProductsMap.get(cheapestStoreForCurrentProduct.getStoreName());
            ProductResponseDto productResponseDto = ProductMapper.extractProductResponseDtoFromStoreProductPriceDto(cheapestStoreForCurrentProduct);
            if(associatedProducts == null) {
                cheapestStoreToProductsMap.put(cheapestStoreForCurrentProduct.getStoreName(), List.of(productResponseDto));
            } else {
                associatedProducts.add(productResponseDto);
                cheapestStoreToProductsMap.put(cheapestStoreForCurrentProduct.getStoreName(), associatedProducts);
            }
        }

        for (Map.Entry<String, List<ProductResponseDto>> entry: cheapestStoreToProductsMap.entrySet()) {
            ShoppingBasketListResponseDto shoppingBasketListResponseDto = new ShoppingBasketListResponseDto();
            String storeName = entry.getKey();
            List<ProductResponseDto> associatedProducts = entry.getValue();
            BigDecimal totalCost = associatedProducts.stream().map((ProductResponseDto::getFinalPrice)).reduce(BigDecimal.ZERO, BigDecimal::add);

            shoppingBasketListResponseDto.setStoreName(storeName);
            shoppingBasketListResponseDto.setProducts(associatedProducts);
            shoppingBasketListResponseDto.setTotalCost(totalCost);
            recommendedShoppingLists.add(shoppingBasketListResponseDto);
        }
        return recommendedShoppingLists;
    }
}
