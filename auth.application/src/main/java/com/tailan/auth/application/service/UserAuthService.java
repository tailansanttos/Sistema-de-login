package com.tailan.auth.application.service;

import com.tailan.auth.application.dtos.user.LoginResponse;
import com.tailan.auth.application.dtos.user.UserLogin;
import com.tailan.auth.application.dtos.user.UserRegister;

public interface UserAuthService {
    public void registerUser(UserRegister userRegister);
    public LoginResponse loginUser(UserLogin userLogin);
}
