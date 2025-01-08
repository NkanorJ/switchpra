package com.transaction.transaction_service.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.transaction.transaction_service.commons.enumeration.Gender;
import com.transaction.transaction_service.commons.enumeration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public record UserDto(
        UUID id, String firstName, String lastName, Gender gender, String password,
        String email, String mobile, @JsonIgnore String secretKey, @JsonIgnore UUID publicId, LocalDate dateOfBirth,
        String accountNumber, String bvn, String nin, Boolean isValidateAccount, Boolean isValidateBvn,
        Boolean isValidateNin,
        Role role) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
