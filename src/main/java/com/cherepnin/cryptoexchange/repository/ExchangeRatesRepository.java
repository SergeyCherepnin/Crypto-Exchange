package com.cherepnin.cryptoexchange.repository;

import com.cherepnin.cryptoexchange.models.ExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRatesRepository extends JpaRepository<ExchangeRates, Long> {
    ExchangeRates findByName(String name);
}
