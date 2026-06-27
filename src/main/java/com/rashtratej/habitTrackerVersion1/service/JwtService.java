package com.rashtratej.habitTrackerVersion1.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY =
            "thisIsMyTemporarySecretKeyForJwtTesting123456";
        // TODO: Move secret key to environment variables

    public String generateToken(Long userId) {

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()
                                + 1000 * 60 * 60 * 24)   // 24 hours
                )
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY
                )
                .compact();
    }

    public Long extractUserId(String token) {

        String subject =
                Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

        return Long.parseLong(subject);
    }

    public boolean validateToken(
            String token,
            Long userId
    ) {

        Long extractedUserId =
                extractUserId(token);

        return extractedUserId.equals(
                userId
        );
    }
}
