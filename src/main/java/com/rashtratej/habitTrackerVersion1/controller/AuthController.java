package com.rashtratej.habitTrackerVersion1.controller;


import com.rashtratej.habitTrackerVersion1.dto.AuthResponse;
import com.rashtratej.habitTrackerVersion1.dto.LoginRequest;
import com.rashtratej.habitTrackerVersion1.dto.RegisterRequest;
import com.rashtratej.habitTrackerVersion1.entity.User;
import com.rashtratej.habitTrackerVersion1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {

        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest request) {

        String token = userService.loginUser(request);

        AuthResponse response =
                new AuthResponse(token, "Login successful");

        return ResponseEntity.ok(response);
    }
}
