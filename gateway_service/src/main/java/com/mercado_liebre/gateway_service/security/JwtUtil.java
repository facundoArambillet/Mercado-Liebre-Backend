package com.mercado_liebre.gateway_service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    @Value("${jwt.secret}")
    private String secret;

    public boolean validateToken(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);
            System.out.println("Token válido");
            return  true;
        } catch (JWTVerificationException e) {
            // El token no es válido
            System.out.println("Token no válido: " + e.getMessage());
            return false;
        }
    }

//    public String getSubject(String token) {
//        return JWT.require(Algorithm.HMAC256(secret))
//                .build().verify(token).getSubject();
//    }
}
