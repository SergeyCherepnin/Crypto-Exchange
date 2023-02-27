package com.cherepnin.cryptoexchange.repository;

import com.cherepnin.cryptoexchange.models.UserCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCurrencyRepository extends JpaRepository<UserCurrency, Long> {


}
