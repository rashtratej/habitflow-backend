package com.rashtratej.habitTrackerVersion1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rashtratej.habitTrackerVersion1.dto.LoginRequest;
import com.rashtratej.habitTrackerVersion1.dto.RegisterRequest;
import com.rashtratej.habitTrackerVersion1.entity.User;
import com.rashtratej.habitTrackerVersion1.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.rashtratej.habitTrackerVersion1.config.JwtAuthenticationFilter;
import com.rashtratej.habitTrackerVersion1.service.JwtService;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void shouldRegisterUser() throws Exception {




        RegisterRequest request = new RegisterRequest();
        request.setUserName("Rashtra");
        request.setEmail("test@test.com");
        request.setPassword("password123");

        User user = new User();
        user.setId(1L);
        user.setUserName("Rashtra");

        when(userService.registerUser(any(RegisterRequest.class)))
                .thenReturn(user);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldLoginUser() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail("test@test.com");
        request.setPassword("password123");

        when(userService.loginUser(any(LoginRequest.class)))
                .thenReturn("dummy-jwt-token");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


}
