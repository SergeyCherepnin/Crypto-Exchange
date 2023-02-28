package com.cherepnin.cryptoexchange.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatesRequestDto {
    String secret_key;
    String currency;
}
