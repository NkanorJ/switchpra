package com.user.user_service.commons.config;


import com.user.user_service.core.domain.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserStorage userStorage;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        var user = userStorage.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(user.email(), user.password(), user.isEnabled(), user.isAccountNonExpired(),
                user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
    }

}
