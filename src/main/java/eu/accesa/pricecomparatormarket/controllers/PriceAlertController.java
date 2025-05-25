package eu.accesa.pricecomparatormarket.controllers;

import eu.accesa.pricecomparatormarket.dtos.PriceAlertRequestDto;
import eu.accesa.pricecomparatormarket.dtos.TriggeredAlertDto;
import eu.accesa.pricecomparatormarket.models.alert.PriceAlert;
import eu.accesa.pricecomparatormarket.services.PriceAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PriceAlertController.BASE_URL)
public class PriceAlertController {

    public static final String BASE_URL = "api/alerts";

    private final PriceAlertService priceAlertService;

    @Autowired
    public PriceAlertController(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PriceAlert createAlert(@RequestBody PriceAlertRequestDto alert) throws Exception {
        return priceAlertService.createAlert(alert);
    }

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public List<TriggeredAlertDto> checkAlerts() {
        return priceAlertService.checkAlerts();
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PriceAlert> getAllAlerts() {
        return priceAlertService.getAllAlerts();
    }
}
