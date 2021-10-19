package com.divary.authentication.controller;

import com.divary.authentication.dto.request.Login;
import com.divary.authentication.dto.request.LoginOtp;
import com.divary.authentication.dto.request.Register;
import com.divary.authentication.dto.response.BaseResponse;
import com.divary.authentication.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController extends BaseController{

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<?>> register(@RequestBody Register form){

        authService.register(form);

        return getResponseCreated(null, "Register Success");
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<?>> login(@RequestBody Login form){
            return getResponseOk(authService.login(form), "oke");
    }

    @PostMapping("/otp")
    public ResponseEntity<BaseResponse<?>> loginOtp(@RequestBody LoginOtp form){
        return getResponseOk(authService.loginOtp(form), "Login Success");
    }

}
