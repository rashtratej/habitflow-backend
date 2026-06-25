package com.rashtratej.habitTrackerVersion1.service;


import com.rashtratej.habitTrackerVersion1.dto.*;
import com.rashtratej.habitTrackerVersion1.entity.User;
import com.rashtratej.habitTrackerVersion1.exception.DuplicateEmailException;
import com.rashtratej.habitTrackerVersion1.exception.InvalidPasswordException;
import com.rashtratej.habitTrackerVersion1.exception.UserNotFoundException;
import com.rashtratej.habitTrackerVersion1.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationService authenticationService
    ) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationService =
                authenticationService;
    }

    public User registerUser(RegisterRequest request) {

        User user = new User();

        user.setUserName(request.getUserName());

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateEmailException(
                    "Email already exists"
            );
        }

        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return user;
    }

//    private User authenticationService.getCurrentUser(); {
//
//        String email =
//                SecurityContextHolder
//                        .getContext()
//                        .getAuthentication()
//                        .getName();
//
//        return userRepository
//                .findByEmail(email)
//                .orElseThrow(() ->
//                        new UserNotFoundException(
//                                "User not found"
//                        )
//                );
//    }

    public String loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found")
                );

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {
            throw new InvalidPasswordException("Invalid password");
        }

        return jwtService.generateToken(user.getEmail());
    }

    public UserResponse getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"
                                )
                        );

        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getProfilePictureUrl()
        );
    }

    public UserResponse updateCurrentUser(UpdateUserRequest request) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new DuplicateEmailException(
                                        "User not found"
                                )
                        );

        // duplicate email check
        if (!user.getEmail().equals(request.getEmail())
                &&
                userRepository.existsByEmail(
                        request.getEmail()
                )) {

            throw new DuplicateEmailException(
                    "Email already exists"
            );
        }

        user.setUserName(
                request.getUserName()
        );

        user.setEmail(
                request.getEmail()
        );

        User updatedUser =
                userRepository.save(user);

        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getUserName(),
                updatedUser.getEmail(),
                updatedUser.getProfilePictureUrl()
        );
    }

    public String changePassword(ChangePasswordRequest request) {

//        User user = authenticationService.getCurrentUser();;
        User user = authenticationService.getCurrentUser();

        boolean matches =
                passwordEncoder.matches(
                        request.getOldPassword(),
                        user.getPassword()
                );

        if (!matches) {
            throw new InvalidPasswordException(
                    "Incorrect old password"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        return "Password updated successfully";
    }

    public String deleteCurrentUser() {

        User user = authenticationService.getCurrentUser();;

        userRepository.delete(user);

        return "Account deleted successfully";
    }
}