package com.rashtratej.habitTrackerVersion1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class AuthResponse {

    private String token;
    private String message;
}
