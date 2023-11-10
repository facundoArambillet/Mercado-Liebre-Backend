package com.mercado_liebre.transaction_service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mercado_liebre.transaction_service.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwt.secret}")
    private String secret;

//    public String createToken(UserDetailsImpl authUser){
//        try {
//            return JWT.create()
//                    .withSubject(authUser.getUsername())
//                    .withIssuedAt(new Date())
//                    .withExpiresAt(Date.from(LocalDateTime.now()
//                            .plusHours(24)
//                            .toInstant(ZoneOffset.of("-03:00")))
//                    )
//                    .sign(Algorithm.HMAC256(secret));
//        }
//        catch (Exception e) {
//            logger.error("Error al crear el token: " + e.getMessage());
//            return null;
//        }
//
//
//    }

    public boolean validateToken(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);
            return  true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build().verify(token).getSubject();
    }
}
