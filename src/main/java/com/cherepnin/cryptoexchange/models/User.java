package com.cherepnin.cryptoexchange.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "created")
    private Date created;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated")
    private Date updated;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCurrency> currencies = new ArrayList<>();


    @Column(name = "key", unique = true)
    private String wallet;

    public User() {}

    public void addCurrency(Currency currency, Double amount) {
        UserCurrency userCurrency = new UserCurrency(this, currency, amount);
        currencies.add(userCurrency);
        currency.getUsers().add(userCurrency);
    }

    public void removeCurrency(Currency currency) {
        for (Iterator<UserCurrency> iterator = currencies.iterator();
             iterator.hasNext(); ) {
            UserCurrency userCurrency = iterator.next();

            if (userCurrency.getUser().equals(this) &&
                    userCurrency.getCurrency().equals(currency)) {
                iterator.remove();
                userCurrency.getCurrency().getUsers().remove(userCurrency);
                userCurrency.setUser(null);
                userCurrency.setCurrency(null);
            }
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
               Objects.equals(email, user.email) &&
               Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }
}
