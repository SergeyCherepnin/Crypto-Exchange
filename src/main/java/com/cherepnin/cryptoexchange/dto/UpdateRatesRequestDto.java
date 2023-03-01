package com.cherepnin.cryptoexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateRatesRequestDto {
    String base_currency;
    Double btc;
    Double rub;
    Double ton;
}
