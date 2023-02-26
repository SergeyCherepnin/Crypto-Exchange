package com.cherepnin.cryptoexchange.service;

import com.cherepnin.cryptoexchange.models.Currency;

import java.util.Map;

public interface AdminService {
    Map<Currency, Double> setChangeRates(Currency baseCurrency, Map<Currency, Double> changeRates);
    Double usersSummaryBalance(Currency currency);
}
