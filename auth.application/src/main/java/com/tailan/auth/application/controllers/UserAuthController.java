package com.tailan.auth.application.controllers;

import com.tailan.auth.application.dtos.user.LoginResponse;
import com.tailan.auth.application.dtos.user.UserLogin;
import com.tailan.auth.application.dtos.user.UserRegister;
import com.tailan.auth.application.service.UserAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserAuthController {
    private final UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserRegister  userRegister) {
        userAuthService.registerUser(userRegister);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody UserLogin userLogin) {
        LoginResponse loginResponse = userAuthService.loginUser(userLogin);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
