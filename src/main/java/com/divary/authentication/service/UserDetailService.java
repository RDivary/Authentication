package com.divary.authentication.service;

import com.divary.authentication.exception.ForbiddenException;
import com.divary.authentication.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    AccountService accountService;

    @Autowired
    public UserDetailService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username){

        Account account = accountService.findByUsername(username)
                .orElseThrow(() -> new  ForbiddenException("User Not Found"));

        return UserDetailsImpl.build(account);
    }

}
