package com.cherepnin.cryptoexchange.controllers;

import com.cherepnin.cryptoexchange.dto.RatesRequestDto;
import com.cherepnin.cryptoexchange.dto.TransactionsRequestDto;
import com.cherepnin.cryptoexchange.dto.UpdateRatesRequestDto;
import com.cherepnin.cryptoexchange.service.AdminService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
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
    public ResponseEntity exchangeRatesUpdate(@RequestBody UpdateRatesRequestDto updateRatesRequestDto,
                                              Principal principal) {
        String baseCurrency = updateRatesRequestDto.getBase_currency();
        Double rub = updateRatesRequestDto.getRub();
        Double btc = updateRatesRequestDto.getBtc();
        Double ton = updateRatesRequestDto.getTon();

        Map<String, Double> newRates = new HashMap<>();
        newRates.put("RUB", rub);
        newRates.put("BTC", btc);
        newRates.put("TON", ton);

        Map<String, Double> response = adminService.updateRates(principal, baseCurrency, newRates);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/amount", produces = {MediaType.APPLICATION_JSON_VALUE,
                                                 MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity totalAmount(@RequestBody RatesRequestDto ratesRequestDto,
                                      Principal principal) {
        String currency = ratesRequestDto.getCurrency();

        Map<String, Double> response = adminService.currencyTotalAmount(principal, currency);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/transactions", produces = {MediaType.APPLICATION_JSON_VALUE,
                                                     MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity transactions(@RequestBody TransactionsRequestDto transactionsRequestDto,
                                       Principal principal) {
        Date dateFrom = transactionsRequestDto.getDate_from();
        Date dateTo = transactionsRequestDto.getDate_to();

        Long count = adminService.getTransactions(principal, dateFrom, dateTo);

        Map<String, Long> response = new HashMap<>();
        response.put("transaction_count", count);

        return ResponseEntity.ok(response);
    }
}
