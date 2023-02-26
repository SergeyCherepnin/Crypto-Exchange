package com.cherepnin.cryptoexchange.controllers;

import com.cherepnin.cryptoexchange.dto.BalanceRequestDto;
import com.cherepnin.cryptoexchange.dto.DepositRequestDto;
import com.cherepnin.cryptoexchange.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;


    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/wallet/balance")
    public ResponseEntity getBalance(@RequestBody BalanceRequestDto balanceRequestDto) {
        Map<String, Double> walletBalance = userService.getWalletBalance(balanceRequestDto.getSecret_key());
        return ResponseEntity.ok(walletBalance);
    }

    @PostMapping("/wallet/deposit")
    public ResponseEntity deposit(@RequestBody DepositRequestDto depositRequestDto) {
        String secretKey = depositRequestDto.getSecret_key();

        Map<String, Double> depositData = new HashMap<>();
        depositData.put("RUB", depositRequestDto.getRUB());
        depositData.put("RUB", depositRequestDto.getBTC());
        depositData.put("RUB", depositRequestDto.getTON());

        Map<String, Double> response = userService.deposit(secretKey, depositData);

        return ResponseEntity.ok(response);
    }

}
