package com.cherepnin.cryptoexchange.service.impl;

import com.cherepnin.cryptoexchange.models.Currency;
import com.cherepnin.cryptoexchange.models.ExchangeRates;
import com.cherepnin.cryptoexchange.models.User;
import com.cherepnin.cryptoexchange.repository.CurrencyRepository;
import com.cherepnin.cryptoexchange.repository.ExchangeRatesRepository;
import com.cherepnin.cryptoexchange.repository.UserRepository;
import com.cherepnin.cryptoexchange.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final ExchangeRatesRepository exchangeRatesRepository;

    private final CurrencyRepository currencyRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            ExchangeRatesRepository exchangeRatesRepository,
                            CurrencyRepository currencyRepository) {
        this.userRepository = userRepository;
        this.exchangeRatesRepository = exchangeRatesRepository;
        this.currencyRepository = currencyRepository;
    }

    @Transactional
    @Override
    public Map<String, Double> updateRates(String walletKey,
                                           String baseCurrency,
                                           Map<String,
                                           Double> newRates) {
        User user = userRepository.findByWallet(walletKey);

        if (user == null) {
            log.warn("IN updateRates - no user found by walletKey: "  + walletKey);
            throw new RuntimeException("Пользователь с таким ключом не найден");
        }

        Map<String, Double> result = new HashMap<>();

        Currency mainCurrency = currencyRepository.findByName(baseCurrency);

        for (Map.Entry<String, Double> entry : newRates.entrySet()) {
            String currencyName = entry.getKey();
            Double newRate = entry.getValue();

            if (newRate == null) {continue;}

            ExchangeRates rates = null;
            ExchangeRates inverseRate = null;

            if (mainCurrency.getName().equals("RUB")) {
                if (currencyName.equals("BTC")) {
                    rates = exchangeRatesRepository.findByName("rubToBtc");
                    rates.setRate(newRate);

                    inverseRate = exchangeRatesRepository.findByName("btcToRub");
                    inverseRate.setRate(1 / newRate);

                    result.put("BTC", newRate);
                }
                if (currencyName.equals("TON")) {
                    rates = exchangeRatesRepository.findByName("rubToTon");
                    rates.setRate(newRate);

                    inverseRate = exchangeRatesRepository.findByName("tonToRub");
                    inverseRate.setRate(1 / newRate);

                    result.put("TON", newRate);
                }
            }

            if (mainCurrency.getName().equals("BTC")) {
                if (currencyName.equals("RUB")) {
                    rates = exchangeRatesRepository.findByName("btcToRub");
                    rates.setRate(entry.getValue());

                    inverseRate = exchangeRatesRepository.findByName("rubToBtc");
                    inverseRate.setRate(1 / entry.getValue());

                    result.put("RUB", newRate);
                }
                if (currencyName.equals("TON")) {
                    rates = exchangeRatesRepository.findByName("btcToTon");
                    rates.setRate(entry.getValue());

                    inverseRate = exchangeRatesRepository.findByName("tonToBtc");
                    inverseRate.setRate(1 / entry.getValue());

                    result.put("TON", newRate);
                }
            }

            if (mainCurrency.getName().equals("TON")) {
                if (currencyName.equals("RUB")) {
                    rates = exchangeRatesRepository.findByName("tonToRub");
                    rates.setRate(entry.getValue());

                    inverseRate = exchangeRatesRepository.findByName("rubToTon");
                    inverseRate.setRate(1 / entry.getValue());

                    result.put("RUB", newRate);
                }
                if (currencyName.equals("BTC")) {
                    rates = exchangeRatesRepository.findByName("tonToBtc");
                    rates.setRate(entry.getValue());

                    inverseRate = exchangeRatesRepository.findByName("btcToTon");
                    inverseRate.setRate(1 / entry.getValue());

                    result.put("BTC", newRate);
                }
            }
            exchangeRatesRepository.saveAll(Arrays.asList(rates, inverseRate));
        }

        return result;
    }
}
