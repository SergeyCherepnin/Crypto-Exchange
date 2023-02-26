package com.cherepnin.cryptoexchange.repository;

import com.cherepnin.cryptoexchange.models.Currency;
import com.cherepnin.cryptoexchange.models.User;
import com.cherepnin.cryptoexchange.models.Wallet;
import com.cherepnin.cryptoexchange.models.WalletCurrencies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
    WalletCurrencies findByWalletId(Long id);

}
