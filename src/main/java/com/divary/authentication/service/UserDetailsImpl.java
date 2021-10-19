package com.divary.authentication.service;

import com.divary.authentication.model.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

    private String id;

    private String username;

    @JsonIgnore
    private String password;

    private String phoneNumber;

    private boolean firstLogin;

    public UserDetailsImpl(String id, String username, String password, String phoneNumber, boolean firstLogin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.firstLogin = firstLogin;
    }

    public static UserDetailsImpl build(Account account) {

        return new UserDetailsImpl(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                account.getPhoneNumber(),
                account.isFirstLogin());
    }

    public String getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
