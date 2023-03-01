package com.cherepnin.cryptoexchange.controllers;

import com.cherepnin.cryptoexchange.dto.RatesRequestDto;
import com.cherepnin.cryptoexchange.service.JointService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController()
@RequestMapping("/rates")
public class RatesController {
    private final JointService jointService;

    public RatesController(JointService jointService) {
        this.jointService = jointService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity getRates(@RequestBody RatesRequestDto ratesRequestDto,
                                   Principal principal) {
        String currency = ratesRequestDto.getCurrency();

        Map<String, Double> response = jointService.getCurrencyRates(principal, currency);

        return ResponseEntity.ok(response);
    }
}
