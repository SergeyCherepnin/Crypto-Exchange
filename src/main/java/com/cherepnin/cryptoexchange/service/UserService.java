package com.cherepnin.cryptoexchange.service;

import com.cherepnin.cryptoexchange.models.User;

import java.security.Principal;
import java.util.Map;

public interface UserService {
    User register(User user);

    User findByUserName(String name);

    Map<String, Double> getWalletBalance(Principal principal);

    Map<String, Double> deposit(Principal principal, Map<String, Double> depositData);

    Map<String, Double> withdraw(Principal principal, Map<String, Double> withDrawData);

    Map<String, String> exchange(Principal principal, String currencyFrom, String currencyTo, Double amount);
}
