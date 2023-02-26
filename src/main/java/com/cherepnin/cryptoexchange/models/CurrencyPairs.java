package com.cherepnin.cryptoexchange.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class CurrencyPairs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rubToTON", nullable = false)
    private Double rubToTON;
    private Double tonToRUB;
    private Double rubToBTC;
    private Double btcToRUB;
    private Double btcToTon;
    private Double tonToBTC;

}
