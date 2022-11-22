package com.habbal.redditclone.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Service
public class JwtProvider {

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(secretKeyFor(SignatureAlgorithm.HS512))
                .compact();
    }
}
