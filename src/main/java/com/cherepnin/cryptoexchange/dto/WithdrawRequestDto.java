package com.cherepnin.cryptoexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WithdrawRequestDto {
    String currency;
    Double count;
    String credit_card;
}
