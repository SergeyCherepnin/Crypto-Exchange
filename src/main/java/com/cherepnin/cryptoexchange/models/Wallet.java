package com.cherepnin.cryptoexchange.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "wallets")
public class Wallet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "wallet_key", unique = true)
    private String walletKey;

    @OneToMany(mappedBy = "wallet")
    private Set<WalletCurrencies> walletCurrencies;


    @OneToOne(mappedBy = "wallet")
    private User user;
}
