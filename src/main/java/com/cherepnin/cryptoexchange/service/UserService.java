package com.cherepnin.cryptoexchange.service;

import com.cherepnin.cryptoexchange.models.Currency;
import com.cherepnin.cryptoexchange.models.User;
import com.cherepnin.cryptoexchange.models.Wallet;

import java.util.Map;

public interface UserService {
    User register(User user, String secretKey);

    User findByUserName(String name);
    Map<String, Double> getWalletBalance(String walletKey);
    Map<String, Double> deposit(String walletKey, Map<String, Double> depositData);
    Wallet withdrawBalance(Currency currency, Double amount, String creditCard);
    Map<Currency, Double> exchangeRates(Currency currency);
    String currencyExchange(Currency currencyFrom, Currency currencyTo, Double amount);
}
