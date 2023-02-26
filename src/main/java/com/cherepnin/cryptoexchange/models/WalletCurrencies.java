package com.cherepnin.cryptoexchange.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "wallet_currencies")
public class WalletCurrencies {
    @Id
    Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_key", referencedColumnName = "wallet_key")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "currency_name", referencedColumnName = "name")
    private Currency currency;

    @Column(name = "amount")
    private Double amount;
}
