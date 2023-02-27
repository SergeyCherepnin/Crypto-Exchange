package com.cherepnin.cryptoexchange.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "user_currency")
public class UserCurrency {

    @EmbeddedId
    private UserCurrencyId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("currencyId")
    private Currency currency;

    @Column(name = "amount")
    private Double amount;

    public UserCurrency() {}

    public UserCurrency(User user, Currency currency, Double amount) {
        this.user = user;
        this.currency = currency;
        this.amount = amount;
        this.id = new UserCurrencyId(user.getId(), currency.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCurrency that = (UserCurrency) o;
        return Objects.equals(user, that.user) &&
               Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, currency);
    }
}
