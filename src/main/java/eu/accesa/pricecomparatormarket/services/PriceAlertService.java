package eu.accesa.pricecomparatormarket.services;

import eu.accesa.pricecomparatormarket.dtos.PriceAlertRequestDto;
import eu.accesa.pricecomparatormarket.dtos.TriggeredAlertDto;
import eu.accesa.pricecomparatormarket.models.alert.PriceAlert;
import eu.accesa.pricecomparatormarket.models.alert.PriceAlertRepository;
import eu.accesa.pricecomparatormarket.models.price.Price;
import eu.accesa.pricecomparatormarket.models.price.PriceRepository;
import eu.accesa.pricecomparatormarket.models.product.Product;
import eu.accesa.pricecomparatormarket.models.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceAlertService {

    private final PriceAlertRepository alertRepository;
    private final PriceRepository priceRepository;

    private final ProductRepository productRepository;

    @Autowired
    public PriceAlertService(PriceAlertRepository alertRepository, PriceRepository priceRepository, ProductRepository productRepository) {
        this.alertRepository = alertRepository;
        this.priceRepository = priceRepository;
        this.productRepository= productRepository;
    }

    public PriceAlert createAlert(PriceAlertRequestDto alertDto ) throws Exception {
        PriceAlert alert =  new PriceAlert();
        Product product = productRepository.findByProductId(alertDto.getProductId()).orElse(null);
        if ( product == null ){
            throw new Exception("non-existent product");
        }
        alert.setProduct(product);

        alert.setTargetPrice(alertDto.getTargetPrice());
        alert.setUserEmail(alertDto.getUserEmail());


        return alertRepository.save(alert);
    }

    public List<TriggeredAlertDto> checkAlerts() {
        List<TriggeredAlertDto> triggeredDtos = new ArrayList<>();
        List<PriceAlert> pendingAlerts = alertRepository.findByTriggeredFalse();

        for (PriceAlert alert : pendingAlerts) {
            Optional<Price> priceOpt = priceRepository.findTopByProduct_ProductIdOrderByEffectiveDateDesc(alert.getProduct().getProductId());

            if (priceOpt.isPresent()) {
                Price price = priceOpt.get();
                BigDecimal currentPrice = price.getPrice();

                if (currentPrice.compareTo(alert.getTargetPrice())<0) {
                    alert.setTriggered(true);
                    alert.setProduct(price.getProduct());
                    
                    alertRepository.save(alert);


                    TriggeredAlertDto dto = new TriggeredAlertDto(
                            alert.getProduct().getProductId(),
                            price.getProduct().getProductName(),
                            alert.getTargetPrice(),
                            currentPrice,
                            true
                    );
                    triggeredDtos.add(dto);
                }
            }
        }

        return triggeredDtos;
    }

    public List<PriceAlert> getAllAlerts() {
        return alertRepository.findAll();
    }
}
