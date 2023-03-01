package com.cherepnin.cryptoexchange.controllers;

import com.cherepnin.cryptoexchange.dto.DepositRequestDto;
import com.cherepnin.cryptoexchange.dto.ExchangeRequestDto;
import com.cherepnin.cryptoexchange.dto.WithdrawRequestDto;
import com.cherepnin.cryptoexchange.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity getBalance(Principal principal) {
        Map<String, Double> response = userService.getWalletBalance(principal);


        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/deposit", produces = {MediaType.APPLICATION_JSON_VALUE,
                                                          MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity deposit(@RequestBody DepositRequestDto depositRequestDto,
                                  Principal principal) {
        String currency = depositRequestDto.getCurrency();
        Double count = Math.abs(depositRequestDto.getCount());

        Map<String, Double> depositData = new HashMap<>();
        depositData.put(currency, count);

        Map<String, Double> response = userService.deposit(principal, depositData);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/withdraw", produces = {MediaType.APPLICATION_JSON_VALUE,
                                                           MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity withdraw(@RequestBody WithdrawRequestDto withdrawRequestDto,
                                   Principal principal) {

        String currency = withdrawRequestDto.getCurrency();
        Double count = Math.abs(withdrawRequestDto.getCount());
        String creditCard = withdrawRequestDto.getCredit_card();

        Map<String, Double> withDrawData = new HashMap<>();
        withDrawData.put(currency, count);

        Map<String, Double> response = userService.withdraw(principal, withDrawData);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/exchange", produces = {MediaType.APPLICATION_JSON_VALUE,
                                                        MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity exchange(@RequestBody ExchangeRequestDto exchangeRequestDto,
                                   Principal principal) {
        String currencyFrom = exchangeRequestDto.getCurrency_from();
        String currencyTo = exchangeRequestDto.getCurrency_to();
        Double amount = Math.abs(exchangeRequestDto.getAmount());

        Map<String, String> response = userService.exchange(principal, currencyFrom, currencyTo, amount);

        return ResponseEntity.ok(response);
    }
}
