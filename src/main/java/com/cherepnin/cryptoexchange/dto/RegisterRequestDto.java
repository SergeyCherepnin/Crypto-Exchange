package com.cherepnin.cryptoexchange.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterRequestDto {
    private String username;
    private String email;
    private String password;
}
