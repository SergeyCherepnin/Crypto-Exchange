package com.cherepnin.cryptoexchange.controllers;

import com.cherepnin.cryptoexchange.dto.BalanceRequestDto;
import com.cherepnin.cryptoexchange.dto.DepositRequestDto;
import com.cherepnin.cryptoexchange.dto.ExchangeRequestDto;
import com.cherepnin.cryptoexchange.dto.WithdrawRequestDto;
import com.cherepnin.cryptoexchange.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/wallet")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/balance", produces = {MediaType.APPLICATION_JSON_VALUE,
                                                         MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity getBalance(@RequestBody BalanceRequestDto balanceRequestDto) {
        String secretKey = balanceRequestDto.getSecret_key();
        Map<String, Double> walletBalance = userService.getWalletBalance(secretKey);

        return ResponseEntity.ok(walletBalance);
    }

    @PostMapping(value = "/deposit", produces = {MediaType.APPLICATION_JSON_VALUE,
                                                          MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity deposit(@RequestBody DepositRequestDto depositRequestDto) {
        String secretKey = depositRequestDto.getSecret_key();
        String currency = depositRequestDto.getCurrency();
        Double count = Math.abs(depositRequestDto.getCount());

        Map<String, Double> depositData = new HashMap<>();
        depositData.put(currency, count);

        Map<String, Double> response = userService.deposit(secretKey, depositData);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/withdraw", produces = {MediaType.APPLICATION_JSON_VALUE,
                                                           MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity withdraw(@RequestBody WithdrawRequestDto withdrawRequestDto) {
        String secretKey = withdrawRequestDto.getSecret_key();
        String currency = withdrawRequestDto.getCurrency();
        Double count = Math.abs(withdrawRequestDto.getCount());
        String creditCard = withdrawRequestDto.getCredit_card();

        Map<String, Double> withDrawData = new HashMap<>();
        withDrawData.put(currency, count);

        Map<String, Double> response = userService.withdraw(secretKey, withDrawData);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/exchange", produces = {MediaType.APPLICATION_JSON_VALUE,
                                                        MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity exchange(@RequestBody ExchangeRequestDto exchangeRequestDto) {
        String secret_key = exchangeRequestDto.getSecret_key();
        String currencyFrom = exchangeRequestDto.getCurrency_from();
        String currencyTo = exchangeRequestDto.getCurrency_to();
        Double amount = Math.abs(exchangeRequestDto.getAmount());

        Map<String, String> response = userService.exchange(secret_key, currencyFrom, currencyTo, amount);

        return ResponseEntity.ok(response);
    }
}
