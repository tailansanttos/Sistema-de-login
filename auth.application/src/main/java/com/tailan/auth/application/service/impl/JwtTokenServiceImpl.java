package com.tailan.auth.application.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtTokenServiceImpl {
    @Value("${SECRET_KEY}")
    private static String SECRET_KEY;
    @Value("${JWT_ISSUER}")
    private static String JWT_ISSUER;

    public String generateToken(UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(JWT_ISSUER)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(userDetails.getUsername())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER) //DEFINE O EMISSOR DO TOKEN
                    .build()
                    .verify(token) //VERIFICA A VALIDADE DO TOKEN
                    .getSubject(); //PEGA O ASSUNTO DO TOKEN, NO CASO O EMAIL DO USUARIO DO TOKEN.
        }catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token inválido ou expirado.", exception);
        }
    }

    private Instant creationDate() {
        return Instant.now();
    }

    private Instant expirationDate() {
        return Instant.now().plusSeconds(3600);
    }
}
