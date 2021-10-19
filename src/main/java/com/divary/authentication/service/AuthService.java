package com.divary.authentication.service;

import com.divary.authentication.dto.request.Login;
import com.divary.authentication.dto.request.LoginOtp;
import com.divary.authentication.dto.request.Register;
import com.divary.authentication.dto.response.JwtResponse;
import com.divary.authentication.dto.response.LoginResponse;
import com.divary.authentication.dto.response.OTPResponse;
import com.divary.authentication.exception.BadRequestException;
import com.divary.authentication.model.Account;
import com.divary.authentication.security.jwt.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthService {

    private static final Logger logger = LogManager.getLogger();

    private AccountService accountService;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

    private UserDetailService userDetailService;

    private OTPService otpService;

    public AuthService(AccountService accountService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserDetailService userDetailService, OTPService otpService) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailService = userDetailService;
        this.otpService = otpService;
    }

    public void register(Register form){

        if (accountService.isUsernameExist(form.getUsername())) throw new BadRequestException("This username isn't available. Please try another.");

        String phoneNumber = form.getPhoneNumber();

        if (form.getPhoneNumber().charAt(0) == '0'){
            phoneNumber = phoneNumber.replaceFirst("0" , "+62");
        }

        Account account = Account.builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .fullName(form.getFullName())
                .phoneNumber(phoneNumber)
                .firstLogin(true)
                .build();

        accountService.save(account);
        logger.info("Username '{}' has bean created", account.getUsername());
    }

    public LoginResponse login(Login form){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Object response = getResponseLogin(userDetails);

        return LoginResponse.builder()
                .otp(userDetails.isFirstLogin())
                .response(response)
                .build();

    }

    public JwtResponse loginOtp(LoginOtp form){

        try {

            String username = otpService.checkedOTP(form);

            UserDetailsImpl userDetails = userDetailService.loadUserByUsername(username);

            return getJwtResponse(userDetails);

        } catch (JsonProcessingException e) {
            logger.error("Error parse user from redis = {}", form.getRandomString());
            throw new com.divary.authentication.exception.JsonProcessingException();
        }

    }

    private Object getResponseLogin(UserDetailsImpl userDetails){

        Object response;

        try {

            if (userDetails.isFirstLogin()){

                Account account = accountService.findById(userDetails.getId());
                account.setFirstLogin(false);
                accountService.save(account);

                response = getJwtResponse(userDetails);

            } else {

                String randomString = RandomStringUtils.random(50, true, true);

                otpService.generatedOTP(userDetails, randomString);

                response = OTPResponse.builder()
                        .randomString(randomString)
                        .build();

            }

            return response;

        } catch (JsonProcessingException e) {

            logger.error("Error parse otp to redis = {}", userDetails.getUsername());
            e.getStackTrace();
            throw new com.divary.authentication.exception.JsonProcessingException();

        }

    }

    private JwtResponse getJwtResponse(UserDetailsImpl userDetails){

        return JwtResponse.builder()
                .username(userDetails.getUsername())
                .token(jwtUtils.generateJwtToken(userDetails))
                .build();

    }

}
