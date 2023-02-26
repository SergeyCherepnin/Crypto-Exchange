package com.cherepnin.cryptoexchange.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DepositRequestDto {
    private String secret_key;
    private Double RUB;
    private Double BTC;
    private Double TON;
}
