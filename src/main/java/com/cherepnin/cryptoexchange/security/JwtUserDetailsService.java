package com.cherepnin.cryptoexchange.security;

import com.cherepnin.cryptoexchange.models.User;
import com.cherepnin.cryptoexchange.security.jwt.JwtUser;
import com.cherepnin.cryptoexchange.security.jwt.JwtUserFactory;
import com.cherepnin.cryptoexchange.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadByUserName - user with username: " + username + " successfully loaded");
        return jwtUser;
    }
}
