package com.cherepnin.cryptoexchange.service;

import java.util.Map;

public interface JointService {
    public Map<String, Double> getCurrencyRates(String walletKey, String currencyName);
}
