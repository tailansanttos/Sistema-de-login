package com.tailan.auth.application.service.impl;

import com.tailan.auth.application.dtos.user.LoginResponse;
import com.tailan.auth.application.dtos.user.UserLogin;
import com.tailan.auth.application.dtos.user.UserRegister;
import com.tailan.auth.application.infra.security.SecurityConfiguration;
import com.tailan.auth.application.model.domain.User;
import com.tailan.auth.application.model.enums.UserRole;
import com.tailan.auth.application.model.repositories.UserRepository;
import com.tailan.auth.application.service.UserAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements UserAuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenServiceImpl jwtTokenServiceImpl;
    private final SecurityConfiguration securityConfiguration;


    public UserAuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenServiceImpl jwtTokenServiceImpl, SecurityConfiguration securityConfiguration) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenServiceImpl = jwtTokenServiceImpl;
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public void registerUser(UserRegister userRegister) {
        User user = userRepository.findByEmail(userRegister.email()).get();
        if (user !=null){
            throw  new IllegalArgumentException("Email already exists");
        }
        String passwordHash = securityConfiguration.passwordEncoder().encode(userRegister.password());

        user.setEmail(userRegister.email());
        user.setRole(UserRole.USER);
        user.setPassword(passwordHash);
        user.setFullName(userRegister.fullName());
        userRepository.save(user);
    }

    @Override
    public LoginResponse loginUser(UserLogin userLogin) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token =  jwtTokenServiceImpl.generateToken(userDetails);
        return new LoginResponse("Login efetuado com sucesso, aqui está seu token.",  token);
    }
}
