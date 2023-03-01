package com.cherepnin.cryptoexchange.service;

import java.security.Principal;
import java.util.Date;
import java.util.Map;

public interface AdminService {
    Map<String, Double> updateRates(Principal principal, String baseCurrency, Map<String, Double> newRates);
    Map<String, Double> currencyTotalAmount(Principal principal, String currencyName);

    Long getTransactions(Principal principal, Date from, Date to);
}
