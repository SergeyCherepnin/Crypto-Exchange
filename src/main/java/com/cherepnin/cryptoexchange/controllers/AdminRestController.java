package com.cherepnin.cryptoexchange.controllers;

import com.cherepnin.cryptoexchange.dto.RatesUpdateDto;
import com.cherepnin.cryptoexchange.service.AdminService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final AdminService adminService;

    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/rates/update", produces = {MediaType.APPLICATION_JSON_VALUE,
                                                        MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity exchangeRatesUpdate(@RequestBody RatesUpdateDto ratesUpdateDto) {
        String secretKey = ratesUpdateDto.getSecret_key();
        String baseCurrency = ratesUpdateDto.getBase_currency();
        Double rub = ratesUpdateDto.getRub();
        Double btc = ratesUpdateDto.getBtc();
        Double ton = ratesUpdateDto.getTon();

        Map<String, Double> newRates = new HashMap<>();
        newRates.put("RUB", rub);
        newRates.put("BTC", btc);
        newRates.put("TON", ton);

        Map<String, Double> response = adminService.updateRates(secretKey, baseCurrency, newRates);

        return ResponseEntity.ok(response);
    }
}
