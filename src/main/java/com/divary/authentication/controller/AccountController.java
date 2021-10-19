package com.divary.authentication.controller;

import com.divary.authentication.dto.response.AboutResponse;
import com.divary.authentication.dto.response.BaseResponse;
import com.divary.authentication.model.Account;
import com.divary.authentication.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/account")
public class AccountController extends BaseController{

    private AccountService accountService;

    private final String JWT = "Authorization";

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/about")
    public ResponseEntity<BaseResponse<?>> about(@RequestHeader(JWT) String jwt){

        Account account = accountService.getAccountByJwt(jwt);

        AboutResponse aboutResponse = AboutResponse.builder()
                .username(account.getUsername())
                .fullName(account.getFullName())
                .build();

        return getResponseOk(aboutResponse, "Register Success");
    }
}
