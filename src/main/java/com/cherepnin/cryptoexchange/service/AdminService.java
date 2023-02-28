package com.cherepnin.cryptoexchange.service;

import java.util.Map;

public interface AdminService {
    Map<String, Double> updateRates(String walletKey, String baseCurrency, Map<String, Double> newRates);
}
