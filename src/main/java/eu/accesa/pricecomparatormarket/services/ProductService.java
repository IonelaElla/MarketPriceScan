package eu.accesa.pricecomparatormarket.services;

import eu.accesa.pricecomparatormarket.dtos.ProductDetailsDto;
import eu.accesa.pricecomparatormarket.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ProductService {
    private CustomRepository customRepository;

    @Autowired
    public void setCustomRepository(CustomRepository customRepository) {
        this.customRepository = customRepository;
    }

    public List<ProductDetailsDto> getTopDiscounts(Integer limit) {
        return customRepository.findTopAvailableDiscounts(limit);
    }

    public List<ProductDetailsDto> getTodayNewDiscounts() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        return customRepository.findDiscountsStartingBy(startDate);
    }
}
