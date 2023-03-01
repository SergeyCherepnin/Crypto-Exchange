package com.cherepnin.cryptoexchange.service.impl;

import com.cherepnin.cryptoexchange.models.Currency;
import com.cherepnin.cryptoexchange.models.ExchangeRates;
import com.cherepnin.cryptoexchange.models.User;
import com.cherepnin.cryptoexchange.repository.*;
import com.cherepnin.cryptoexchange.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final ExchangeRatesRepository exchangeRatesRepository;
    private final UserCurrencyRepository userCurrencyRepository;
    private final CurrencyRepository currencyRepository;
    private final TransactionRepository transactionRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            ExchangeRatesRepository exchangeRatesRepository,
                            UserCurrencyRepository userCurrencyRepository,
                            CurrencyRepository currencyRepository,
                            TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.exchangeRatesRepository = exchangeRatesRepository;
        this.userCurrencyRepository = userCurrencyRepository;
        this.currencyRepository = currencyRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    @Override
    public Map<String, Double> updateRates(Principal principal, String baseCurrency,
                                           Map<String,
                                           Double> newRates) {
        User user = userRepository.findByUsername(principal.getName());

        if (user == null) {
            log.warn("IN updateRates - no user found");
            throw new RuntimeException("Пользователь не найден");
        }

        Map<String, Double> result = new HashMap<>();

        Currency mainCurrency = currencyRepository.findByName(baseCurrency);

        //TODO сделать рефакторинг метода
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

    @Override
    public Map<String, Double> currencyTotalAmount(Principal principal, String currencyName) {
        User user = userRepository.findByUsername(principal.getName());
        Currency currency = currencyRepository.findByName(currencyName);

        if (user == null || currency == null) {
            log.warn("IN totalAmount - no user or currency found");
            throw new RuntimeException("Нет пользователя или валлюты с таким именем");
        }

        List<Double> allAmounts = userCurrencyRepository.findAmountsByCurrencyId(currency.getId());
        double total = 0;

        for(Double amount : allAmounts) {
            total += amount;
        }

        Map<String, Double> result = new HashMap<>();
        result.put(currency.getName(), total);

        return result;
    }

    @Override
    public Long getTransactions(Principal principal, Date from, Date to) {
        User user = userRepository.findByUsername(principal.getName());

        if (user == null) {
            log.warn("IN updateRates - no user found");
            throw new RuntimeException("Пользователь не найден");
        }

        long count = transactionRepository.findAllByCreatedAfterAndCreatedBefore(from, to);

        return count;
    }
}
