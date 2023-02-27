package com.cherepnin.cryptoexchange.repository;

import com.cherepnin.cryptoexchange.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByName(String name);
}
