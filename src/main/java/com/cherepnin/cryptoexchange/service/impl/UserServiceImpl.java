package com.cherepnin.cryptoexchange.service.impl;

import com.cherepnin.cryptoexchange.models.*;
import com.cherepnin.cryptoexchange.models.Currency;
import com.cherepnin.cryptoexchange.repository.RoleRepository;
import com.cherepnin.cryptoexchange.repository.UserRepository;
import com.cherepnin.cryptoexchange.repository.WalletRepository;
import com.cherepnin.cryptoexchange.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final WalletRepository walletRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           WalletRepository walletRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user, String secretKey) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setWallet(new Wallet());
        user.getWallet().setWalletKey(secretKey);
        User registeredUser = userRepository.save(user);

        log.info("IN register - user: " + registeredUser + " successfully registered");
        return registeredUser;
    }

    @Override
    public User findByUserName(String name) {
        User result = userRepository.findByUsername(name);
        if (result == null) {
            log.warn("IN findByUsername - no user found by name: " + name);
        }
        log.info("IN findByUserName - user: " + result + " found by username");
        return result;
    }

    @Override
    public Map<String, Double> getWalletBalance(String walletKey) {
        Set<WalletCurrencies> walletCurrencies = walletRepository.getWalletCurrenciesByWalletWalletKey(walletKey);

        if (walletCurrencies == null) {
            log.warn("IN getWalletBalance - no wallet by secret_key: " + walletKey);
        }

        Map<String, Double> walletBalance = new HashMap<>();

        for (WalletCurrencies element : walletCurrencies) {
            walletBalance.put(element.getCurrency().getName(), element.getAmount());
        }
       return walletBalance;
    }

    @Override
    public Map<String, Double> deposit(String walletKey, Map<String, Double> depositData) {
        Set<WalletCurrencies> walletCurrencies = walletRepository.getWalletCurrenciesByWalletWalletKey(walletKey);

        if (walletCurrencies == null) {
            log.warn("IN deposit - no wallet by secret_key: " + walletKey);
        }

        for (WalletCurrencies currency: walletCurrencies) {
            String currencyName = currency.getCurrency().getName();

            if (depositData.containsKey(currencyName)) {
                Double oldAmount = currency.getAmount();
                Double newAmount = oldAmount + depositData.get(currencyName);
                currency.setAmount(newAmount);
            }
        }

        walletRepository.saveAll(walletCurrencies);
        return getWalletBalance(walletKey);
    }

    @Override
    public Wallet withdrawBalance(Currency currency, Double amount, String creditCard) {
        return null;
    }

    @Override
    public Map<Currency, Double> exchangeRates(Currency currency) {
        return null;
    }

    @Override
    public String currencyExchange(Currency currencyFrom, Currency currencyTo, Double amount) {
        return null;
    }
}
