package com.rashtratej.habitTrackerVersion1.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private final JwtService jwtService =
            new JwtService();

    @Test
    void shouldGenerateToken() {

        String token =
                jwtService.generateToken(1L);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldExtractCorrectUserId() {

        String token =
                jwtService.generateToken(5L);

        Long extractedUserId =
                jwtService.extractUserId(token);

        assertEquals(
                5L,
                extractedUserId
        );
    }

    @Test
    void shouldValidateCorrectToken() {

        String token =
                jwtService.generateToken(10L);

        boolean valid =
                jwtService.validateToken(
                        token,
                        10L
                );

        assertTrue(valid);
    }

    @Test
    void shouldRejectWrongUserId() {

        String token =
                jwtService.generateToken(10L);

        boolean valid =
                jwtService.validateToken(
                        token,
                        20L
                );

        assertFalse(valid);
    }



}