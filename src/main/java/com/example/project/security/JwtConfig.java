package com.example.project.security;

import com.example.project.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtConfig {

    private final SecretKey secretKey;

    public JwtConfig(@Value("${api.jwt.secret}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    @Value("${api.jwt.expiration}")
    private long EXPIRATION;

    public String createJWT(User user) {
        Date now = new Date();
        Date expirationData = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer("localhost:8080")
                .setIssuedAt(new Date())
                .setExpiration(expirationData)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UUID getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return UUID.fromString(claims.getSubject());
    }
}
