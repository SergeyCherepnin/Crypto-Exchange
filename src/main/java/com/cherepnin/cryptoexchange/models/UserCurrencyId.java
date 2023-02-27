package com.cherepnin.cryptoexchange.models;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public class UserCurrencyId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "currency_id")
    private Long currencyId;

    public UserCurrencyId(){}

    public UserCurrencyId(Long userId, Long currencyId) {
        this.userId = userId;
        this.currencyId = currencyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCurrencyId that = (UserCurrencyId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, currencyId);
    }
}
