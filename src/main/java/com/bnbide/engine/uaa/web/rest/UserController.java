package com.bnbide.engine.uaa.web.rest;

import com.bnbide.engine.uaa.service.UserService;
import com.bnbide.engine.uaa.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/v1/user", produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest body){
        userService.register(body.getIdentifier(), body.getPhoneNumber());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register-verify")
    public ResponseEntity<RegisterVerifyResponse> registerVerify(@Valid @RequestBody RegisterVerifyRequest body){
        Token token = userService.authenticate(body.getIdentifier(), body.getPinCode());
        return new ResponseEntity<>(new RegisterVerifyResponse().token(token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest body){
        userService.login(body.getIdentifier(), body.getPhoneNumber());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login-verify")
    public ResponseEntity<LoginVerifyResponse> loginVerify(@Valid @RequestBody LoginVerifyRequest body){
        Token token = userService.authenticate(body.getIdentifier(), body.getPinCode());
        return new ResponseEntity<>(new LoginVerifyResponse().token(token), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest body){
        Token token = userService.refreshToken(body.getRefreshToken());
        return new ResponseEntity<>(new RefreshTokenResponse().token(token), HttpStatus.OK);
    }
}
