package com.cherepnin.cryptoexchange.service.impl;

import com.cherepnin.cryptoexchange.models.Currency;
import com.cherepnin.cryptoexchange.models.*;
import com.cherepnin.cryptoexchange.repository.*;
import com.cherepnin.cryptoexchange.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserCurrencyRepository userCurrencyRepository;
    private final RoleRepository roleRepository;
    private final CurrencyRepository currencyRepository;
    private final ExchangeRatesRepository exchangeRatesRepository;
    private final TransactionRepository transactionRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserCurrencyRepository userCurrencyRepository,
                           RoleRepository roleRepository,
                           CurrencyRepository currencyRepository,
                           ExchangeRatesRepository exchangeRatesRepository,
                           TransactionRepository transactionRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userCurrencyRepository = userCurrencyRepository;
        this.roleRepository = roleRepository;
        this.currencyRepository = currencyRepository;
        this.exchangeRatesRepository = exchangeRatesRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        Currency rub = currencyRepository.findByName("RUB");
        Currency btc = currencyRepository.findByName("BTC");
        Currency ton = currencyRepository.findByName("TON");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.addCurrency(rub, 0.00);
        user.addCurrency(btc, 0.00);
        user.addCurrency(ton, 0.00);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: " + registeredUser + " successfully registered");
        return registeredUser;
    }

    @Override
    public User findByUserName(String name) {
        User result = userRepository.findByUsername(name);
        if (result == null) {
            log.warn("IN findByUsername - no user found by name: " + name);
            throw new RuntimeException("Пользователь с таким ключом не найден");
        }

        log.info("IN findByUserName - user: " + result + " found by username");
        return result;
    }

    @Override
    public Map<String, Double> getWalletBalance(Principal principal) {
        User user = findByUserName(principal.getName());

        Map<String, Double> balance = new HashMap<>();

        List<UserCurrency> userCurrencies = user.getCurrencies();

        for (UserCurrency userCurrency : userCurrencies) {
            balance.put(userCurrency.getCurrency().getName(), userCurrency.getAmount());
        }

        return balance;
    }

    @Transactional
    @Override
    public Map<String, Double> deposit(Principal principal, Map<String, Double> depositData) {
        User user = findByUserName(principal.getName());
        if (depositData == null) {
            log.warn("IN deposit - no data");
            throw new RuntimeException("Введите корректные данные");
        }

        for (Map.Entry<String, Double> entry : depositData.entrySet()) {
            for (UserCurrency wallet : user.getCurrencies()) {
                String depositCurrency = entry.getKey();
                String walletCurrency = wallet.getCurrency().getName();

                if (depositCurrency.equals(walletCurrency)) {
                    Double oldAmount = wallet.getAmount();
                    Double addAmount = entry.getValue();
                    Double newAmount = oldAmount + addAmount;

                    wallet.setAmount(newAmount);

                    userCurrencyRepository.save(wallet);
                }
            }
        }
        transactionRepository.save(new Transaction("deposit"));

        log.info("IN deposit - operation completed successfully by user: " + user);
        return getWalletBalance(principal);
    }

    @Transactional
    @Override
    public Map<String, Double> withdraw(Principal principal, Map<String, Double> withDrawData) {
        User user = findByUserName(principal.getName());

        if (withDrawData == null) {
            log.warn("IN withdraw - no data");
            throw new RuntimeException("Введите корректные данные");
        }

        for (Map.Entry<String, Double> entry : withDrawData.entrySet()) {
            for (UserCurrency wallet : user.getCurrencies()) {
                String withdrawCurrency = entry.getKey();
                String walletCurrency = wallet.getCurrency().getName();

                if (withdrawCurrency.equals(walletCurrency)) {
                    Double oldAmount = wallet.getAmount();
                    Double withdrawAmount = entry.getValue();

                    if (oldAmount < withdrawAmount) {
                        throw new RuntimeException("Недостаточно средств на счету!");
                    }

                    Double newAmount = oldAmount - withdrawAmount;
                    wallet.setAmount(newAmount);

                    userCurrencyRepository.save(wallet);
                }
            }
        }
        transactionRepository.save(new Transaction("withdraw"));

        log.info("IN withdraw - operation completed successfully by user: " + user);
        return getWalletBalance(principal);
    }

    @Transactional
    @Override
    public Map<String, String> exchange(Principal principal,
                                        String currencyFrom,
                                        String currencyTo,
                                        Double amount) {

        User user = findByUserName(principal.getName());

        Map<String, String> result = new HashMap<>();

        Currency currencyOutput = currencyRepository.findByName(currencyFrom);
        Currency currencyInput = currencyRepository.findByName(currencyTo);

        if (currencyOutput == null || currencyInput == null) {
            throw new RuntimeException("Таких валют не найдено");
        }

            UserCurrency userCurrencyFrom = userCurrencyRepository
                .findUserCurrencyByUserAndCurrency(user.getId(), currencyOutput.getId());
        UserCurrency userCurrencyTo = userCurrencyRepository
                .findUserCurrencyByUserAndCurrency(user.getId(), currencyInput.getId());

        ExchangeRates rates = null;

        if (currencyFrom.equals("RUB")) {
            if (currencyTo.equals("BTC")) {
                rates = exchangeRatesRepository.findByName("rubToBtc");
            }
            if (currencyTo.equals("TON")) {
                rates = exchangeRatesRepository.findByName("rubToTon");
            }
        }
        if (currencyFrom.equals("BTC")) {
            if (currencyTo.equals("RUB")) {
                rates = exchangeRatesRepository.findByName("btcToRub");
            }
            if (currencyTo.equals("TON")) {
                rates = exchangeRatesRepository.findByName("btcToTon");
            }
        }
        if (currencyFrom.equals("TON")) {
            if (currencyTo.equals("RUB")) {
                rates = exchangeRatesRepository.findByName("tonToRub");
            }
            if (currencyTo.equals("BTC")) {
                rates = exchangeRatesRepository.findByName("tonToBtc");
            }
        }

        Double oldAmountFrom = userCurrencyFrom.getAmount();
        Double newAmountFrom;
        Double oldAmountTo = userCurrencyTo.getAmount();
        Double newAmountTo;

        if (oldAmountFrom < amount) {
            throw new RuntimeException("Недостаточно средств!");
        }

        newAmountFrom = oldAmountFrom - amount;
        Double exchange =  amount * rates.getRate();
        newAmountTo = oldAmountTo + exchange;

        userCurrencyFrom.setAmount(newAmountFrom);
        userCurrencyTo.setAmount(newAmountTo);
        userCurrencyRepository.saveAll(Arrays.asList(userCurrencyFrom, userCurrencyTo));

        result.put("currency_from", currencyFrom);
        result.put("currency_to", currencyTo);
        result.put("amount_from", amount.toString());
        result.put("amount_to", exchange.toString());

        transactionRepository.save(new Transaction("exchange"));

        log.info("In exchange - operation completed successfully by user: " + user);
    return result;
    }
}


