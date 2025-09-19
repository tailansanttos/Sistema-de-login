package com.tailan.auth.application.infra.security;

import com.tailan.auth.application.model.domain.User;
import com.tailan.auth.application.model.repositories.UserRepository;
import com.tailan.auth.application.service.impl.JwtTokenServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JwtTokenServiceImpl  jwtTokenServiceImpl;

    public UserAuthenticationFilter(UserRepository userRepository, JwtTokenServiceImpl jwtTokenServiceImpl) {
        this.userRepository = userRepository;
        this.jwtTokenServiceImpl = jwtTokenServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String token = recoveryToken(request); //recupera token do  cabecalho

            if (token !=null){
                String subject = jwtTokenServiceImpl.getSubjectFromToken(token);
                Optional<User> userOptional = userRepository.findByEmail(subject);
                if (userOptional.isPresent()) {
                    UserDetailsImpl userDetails = new UserDetailsImpl(userOptional.get());
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request,response);

    }

    private String recoveryToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null){
            return authorization.replace("Bearer ", "");
        }
        return null;
    }


}
