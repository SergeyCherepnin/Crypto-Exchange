package com.cherepnin.cryptoexchange.repository;

import com.cherepnin.cryptoexchange.models.WalletCurrencies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface WalletRepository extends JpaRepository<WalletCurrencies, String> {

    Set<WalletCurrencies> getWalletCurrenciesByWalletWalletKey(String secretKey);
}
