package com.divary.authentication.service;

import com.divary.authentication.exception.NotFoundException;
import com.divary.authentication.model.Account;
import com.divary.authentication.repository.AccountRepository;
import com.divary.authentication.security.jwt.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    private JwtUtils jwtUtils;

    public AccountService(AccountRepository accountRepository, JwtUtils jwtUtils) {
        this.accountRepository = accountRepository;
        this.jwtUtils = jwtUtils;
    }

    public Account save(Account account){
        return accountRepository.save(account);
    }

    public Account findById(String id){
        return accountRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Account getAccountByJwt(String jwt){

        String username = jwtUtils.getUserNameFromJwtToken(getJwt(jwt));
        return findByUsername(username).orElseThrow(() -> new NotFoundException("Username not found"));

    }

    public Optional<Account> findByUsername(String username){
        return accountRepository.findByUsername(username);
    }

    public boolean isUsernameExist(String username){
        return accountRepository.existsByUsername(username);
    }

    private String getJwt(String jwt){
        return jwt.substring(7);
    }

    public void saveAll(List<Account> accountList){
        accountRepository.saveAll(accountList);
    }

}
