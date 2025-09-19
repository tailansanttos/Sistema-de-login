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

        if (checkIfEndpointIsNotPulic(request)){
            String token = recoveryToken(request); //recupera token do  cabecalho
            if (token !=null){
                String subject = jwtTokenServiceImpl.getSubjectFromToken(token);
                User user = userRepository.findByEmail(subject).get();
                Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                throw new RuntimeException("O token está ausente");
            }
            filterChain.doFilter(request,response);
        }
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null){
            return authorization.replace("Bearer ", "");
        }
        return null;
    }








    //VERIFICA SE O ENDPOINT CHAMADO REQUER AUTENTICAÇÃO ANTES DE PROCESSAR A REQUISIÇÃO
    private boolean checkIfEndpointIsNotPulic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUITED).contains(requestURI);

    }
}
