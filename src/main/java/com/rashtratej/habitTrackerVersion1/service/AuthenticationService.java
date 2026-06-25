package com.rashtratej.habitTrackerVersion1.service;

import com.rashtratej.habitTrackerVersion1.entity.User;
import com.rashtratej.habitTrackerVersion1.exception.UserNotFoundException;
import com.rashtratej.habitTrackerVersion1.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        )
                );
    }
}