package com.cherepnin.cryptoexchange.repository;

import com.cherepnin.cryptoexchange.models.UserCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCurrencyRepository extends JpaRepository<UserCurrency, Long> {
    @Query(nativeQuery = true,
           value = "SELECT * FROM user_currency WHERE user_id= :userId AND currency_id= :currencyId")
    UserCurrency findUserCurrencyByUserAndCurrency(Long userId, Long currencyId);

    @Query(nativeQuery = true,
           value = "SELECT amount FROM user_currency WHERE currency_id = :currencyId")
    List<Double> findAmountsByCurrencyId(Long currencyId);

}
