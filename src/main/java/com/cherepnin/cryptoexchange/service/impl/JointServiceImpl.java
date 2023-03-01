package com.cherepnin.cryptoexchange.service.impl;

import com.cherepnin.cryptoexchange.models.Currency;
import com.cherepnin.cryptoexchange.models.ExchangeRates;
import com.cherepnin.cryptoexchange.models.User;
import com.cherepnin.cryptoexchange.repository.CurrencyRepository;
import com.cherepnin.cryptoexchange.repository.ExchangeRatesRepository;
import com.cherepnin.cryptoexchange.repository.UserRepository;
import com.cherepnin.cryptoexchange.service.JointService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Service
public class JointServiceImpl implements JointService {
    private final UserRepository userRepository;
    private final ExchangeRatesRepository exchangeRatesRepository;
    private final CurrencyRepository currencyRepository;

    public JointServiceImpl(UserRepository userRepository,
                            ExchangeRatesRepository exchangeRatesRepository,
                            CurrencyRepository currencyRepository) {
        this.userRepository = userRepository;
        this.exchangeRatesRepository = exchangeRatesRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Map<String, Double> getCurrencyRates(Principal principal, String currencyName) {
        User user = userRepository.findByUsername(principal.getName());
        Currency currency = currencyRepository.findByName(currencyName);

        if (user == null || currency == null) {
            throw new RuntimeException("Такого пользователя или валюты не существует");
        }

        Map<String, Double> rates = new HashMap<>();

        if (currencyName.equals("RUB")) {
            ExchangeRates rubToBtc = exchangeRatesRepository.findByName("rubToBtc");
            ExchangeRates rubToTon = exchangeRatesRepository.findByName("rubToTon");

            rates.put("BTC", rubToBtc.getRate());
            rates.put("TON", rubToTon.getRate());

            return rates;
        }

        if (currencyName.equals("BTC")) {
            ExchangeRates btcToRub = exchangeRatesRepository.findByName("btcToRub");
            ExchangeRates btcToTon = exchangeRatesRepository.findByName("btcToTon");

            rates.put("RUB", btcToRub.getRate());
            rates.put("TON", btcToTon.getRate());

            return rates;
        }

        if (currencyName.equals("TON")) {
            ExchangeRates tonToRub = exchangeRatesRepository.findByName("tonToRub");
            ExchangeRates tonToBtc = exchangeRatesRepository.findByName("tonToBtc");

            rates.put("RUB", tonToRub.getRate());
            rates.put("BTC", tonToBtc.getRate());
        }

        return rates;
    }
}
