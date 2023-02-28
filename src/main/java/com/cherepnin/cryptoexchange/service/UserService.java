package com.cherepnin.cryptoexchange.service;

import com.cherepnin.cryptoexchange.models.User;

import java.util.Map;

public interface UserService {
    User register(User user, String secretKey);

    User findByUserName(String name);

    Map<String, Double> getWalletBalance(String walletKey);

    Map<String, Double> deposit(String walletKey, Map<String, Double> depositData);

    Map<String, Double> withdraw(String walletKey, Map<String, Double> withDrawData);

    Map<String, String> exchange(String walletKey, String currencyFrom, String currencyTo, Double amount);
}
