package com.rashtratej.habitTrackerVersion1.service;

import com.rashtratej.habitTrackerVersion1.dto.ChangePasswordRequest;
import com.rashtratej.habitTrackerVersion1.dto.LoginRequest;
import com.rashtratej.habitTrackerVersion1.dto.RegisterRequest;
import com.rashtratej.habitTrackerVersion1.entity.User;
import com.rashtratej.habitTrackerVersion1.exception.DuplicateEmailException;
import com.rashtratej.habitTrackerVersion1.exception.InvalidPasswordException;
import com.rashtratej.habitTrackerVersion1.exception.UserNotFoundException;
import com.rashtratej.habitTrackerVersion1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import com.rashtratej.habitTrackerVersion1.repository.HabitRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request =
                new LoginRequest();

        request.setEmail("test@gmail.com");
        request.setPassword("password123");

        User user =
                new User();

        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "password123",
                "encodedPassword"
        )).thenReturn(true);

        when(jwtService.generateToken(1L))
                .thenReturn("fake-jwt-token");

        String token =
                userService.loginUser(request);

        assertEquals(
                "fake-jwt-token",
                token
        );
    }

    @Test
    void shouldThrowExceptionForInvalidPassword() {

        LoginRequest request =
                new LoginRequest();

        request.setEmail("test@gmail.com");
        request.setPassword("wrongPassword");

        User user =
                new User();

        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "wrongPassword",
                "encodedPassword"
        )).thenReturn(false);

        assertThrows(
                InvalidPasswordException.class,
                () -> userService.loginUser(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        LoginRequest request =
                new LoginRequest();

        request.setEmail("unknown@gmail.com");
        request.setPassword("password");

        when(userRepository.findByEmail(
                "unknown@gmail.com"
        )).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.loginUser(request)
        );
    }

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterRequest request =
                new RegisterRequest();

        request.setUserName("alpha");
        request.setEmail("alpha@gmail.com");
        request.setPassword("password123");

        when(userRepository.existsByEmail(
                "alpha@gmail.com"
        )).thenReturn(false);

        when(passwordEncoder.encode(
                "password123"
        )).thenReturn("encodedPassword");

        User savedUser =
                new User();

        savedUser.setId(1L);
        savedUser.setEmail("alpha@gmail.com");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        User result =
                userService.registerUser(request);

        assertNotNull(result);
        assertEquals(
                "alpha@gmail.com",
                result.getEmail()
        );
    }

    @Test
    void shouldThrowExceptionForDuplicateEmail() {

        RegisterRequest request =
                new RegisterRequest();

        request.setUserName("alpha");
        request.setEmail("alpha@gmail.com");
        request.setPassword("password123");

        when(userRepository.existsByEmail(
                "alpha@gmail.com"
        )).thenReturn(true);

        assertThrows(
                DuplicateEmailException.class,
                () -> userService.registerUser(request)
        );
    }

    @Test
    void shouldChangePasswordSuccessfully() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setOldPassword("oldPass");
        request.setNewPassword("newPass");

        User user =
                new User();

        user.setPassword("encodedOldPassword");

        when(authenticationService.getCurrentUser())
                .thenReturn(user);

        when(passwordEncoder.matches(
                "oldPass",
                "encodedOldPassword"
        )).thenReturn(true);

        when(passwordEncoder.encode(
                "newPass"
        )).thenReturn("encodedNewPassword");

        String result =
                userService.changePassword(request);

        assertEquals(
                "Password updated successfully",
                result
        );

        verify(userRepository, times(1))
                .save(user);
    }

    @Test
    void shouldThrowExceptionForIncorrectOldPassword() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setOldPassword("wrongPassword");
        request.setNewPassword("newPass");

        User user =
                new User();

        user.setPassword("encodedOldPassword");

        when(authenticationService.getCurrentUser())
                .thenReturn(user);

        when(passwordEncoder.matches(
                "wrongPassword",
                "encodedOldPassword"
        )).thenReturn(false);

        assertThrows(
                InvalidPasswordException.class,
                () -> userService.changePassword(request)
        );

        verify(userRepository, never())
                .save(any());
    }





}