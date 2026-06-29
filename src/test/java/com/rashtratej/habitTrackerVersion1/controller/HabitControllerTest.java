package com.rashtratej.habitTrackerVersion1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rashtratej.habitTrackerVersion1.config.JwtAuthenticationFilter;
import com.rashtratej.habitTrackerVersion1.dto.*;
import com.rashtratej.habitTrackerVersion1.service.HabitService;
import com.rashtratej.habitTrackerVersion1.service.JwtService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HabitController.class)
@Import(HabitControllerTest.TestConfig.class)
public class HabitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HabitService habitService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @TestConfiguration
    static class TestConfig { }

    @Test
    void shouldCreateHabit() throws Exception {

        CreateHabitRequest request = new CreateHabitRequest();
        request.setTitle("Running");
        request.setDescription("Morning run");

        HabitResponse response =
                new HabitResponse(
                        1L,
                        "Running",
                        "Morning run",
                        false,
                        java.time.LocalDateTime.now()
                );

        when(habitService.createHabit(any(CreateHabitRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/habits/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetMyHabits() throws Exception {

        PaginatedHabitResponse response =
                new PaginatedHabitResponse(
                        List.of(),
                        0,
                        1,
                        0L
                );

        when(habitService.getMyHabits(0,5,"createdAt","asc"))
                .thenReturn(response);

        mockMvc.perform(get("/habits/my"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void shouldCompleteHabit() throws Exception {

        HabitResponse response =
                new HabitResponse(
                        1L,
                        "Running",
                        "Morning run",
                        true,
                        LocalDateTime.now()
                );

        when(habitService.markHabitComplete(1L))
                .thenReturn(response);

        mockMvc.perform(patch("/habits/1/complete"))
                .andExpect(status().isForbidden());
    }


    @Test
    void shouldDeleteHabit() throws Exception {

        when(habitService.deleteHabit(1L))
                .thenReturn("Deleted");

        mockMvc.perform(delete("/habits/1"))
                .andExpect(status().isForbidden());
    }


    @Test
    void shouldGetHabitStats() throws Exception {

        HabitStatsResponse stats = new HabitStatsResponse();
        when(habitService.getHabitStats())
                .thenReturn(stats);

        mockMvc.perform(get("/habits/stats"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void shouldUpdateHabit() throws Exception {

        UpdateHabitRequest request =
                new UpdateHabitRequest();

        request.setTitle("Gym");
        request.setDescription("Evening gym");

        HabitResponse response =
                new HabitResponse(
                        1L,
                        "Gym",
                        "Evening gym",
                        false,
                        LocalDateTime.now()
                );

        when(habitService.updateHabit(eq(1L), any(UpdateHabitRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/habits/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

}