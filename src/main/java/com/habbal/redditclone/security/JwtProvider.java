package com.habbal.redditclone.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;

import static java.util.Date.from;


@Service
@RequiredArgsConstructor
public class JwtProvider {
    private final SecretKey secretKey;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMilliseconds;

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(from(Instant.now()))
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMilliseconds)))
                .signWith(secretKey)
                .compact();
    }
    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMilliseconds)))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String jwt) {
        Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromToken(String jwt) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMilliseconds;
    }

}
