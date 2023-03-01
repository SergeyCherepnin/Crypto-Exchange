package com.cherepnin.cryptoexchange.repository;

import com.cherepnin.cryptoexchange.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(nativeQuery = true,
           value = "SELECT count(id) FROM transactions WHERE created BETWEEN :after AND :before")
    Long findAllByCreatedAfterAndCreatedBefore(Date after, Date before);
}
