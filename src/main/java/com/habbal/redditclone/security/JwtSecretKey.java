package com.habbal.redditclone.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

}
