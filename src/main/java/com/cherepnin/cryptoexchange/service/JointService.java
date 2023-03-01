package com.cherepnin.cryptoexchange.service;

import java.security.Principal;
import java.util.Map;

public interface JointService {
    public Map<String, Double> getCurrencyRates(Principal principal, String currencyName);

}
