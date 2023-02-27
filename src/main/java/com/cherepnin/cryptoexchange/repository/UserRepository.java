package com.cherepnin.cryptoexchange.repository;

import com.cherepnin.cryptoexchange.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
    User findByWallet(String key);
}
