package com.cherepnin.cryptoexchange.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExchangeRequestDto {
    private String currency_from;
    private String currency_to;
    private Double amount;
}
