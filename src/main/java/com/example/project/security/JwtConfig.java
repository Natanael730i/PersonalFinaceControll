package com.example.project.security;

import com.example.project.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.UUID;

public class JwtConfig {

    @Value("${api.jwt.secret}")
    private String KEY;

    @Value("${api.jwt.expiration}")
    private long EXPIRATION;

    public String createJWT(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date();
        Date expirationData = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer("localhost:8080")
                .setIssuedAt(new Date())
                .setExpiration(expirationData)
                .signWith(SignatureAlgorithm.RS512, KEY)
                .compact();
    }

    public Boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UUID getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        return UUID.fromString(claims.getSubject());
    }
}
