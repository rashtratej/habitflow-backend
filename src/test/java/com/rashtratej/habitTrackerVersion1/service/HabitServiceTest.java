package com.rashtratej.habitTrackerVersion1.service;

import com.rashtratej.habitTrackerVersion1.dto.CreateHabitRequest;
//import com.rashtratej.habitTrackerVersion1.dto.HabitResponse;
import com.rashtratej.habitTrackerVersion1.dto.HabitResponse;
import com.rashtratej.habitTrackerVersion1.dto.HabitStatsResponse;
import com.rashtratej.habitTrackerVersion1.dto.UpdateHabitRequest;
import com.rashtratej.habitTrackerVersion1.entity.Habit;
import com.rashtratej.habitTrackerVersion1.entity.User;
import com.rashtratej.habitTrackerVersion1.exception.HabitNotFoundException;

import com.rashtratej.habitTrackerVersion1.repository.HabitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HabitServiceTest {

    @Mock
    private HabitRepository habitRepository;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private HabitService habitService;

    @Test
    void shouldCreateHabitSuccessfully() {

        CreateHabitRequest request = new CreateHabitRequest();

        request.setTitle("Morning Run");
        request.setDescription("Run 3km");

        User user = new User();

        user.setId(1L);

        Habit savedHabit = new Habit();

        savedHabit.setTitle("Morning Run");
        savedHabit.setDescription("Run 3km");

        when(authenticationService.getCurrentUser()).thenReturn(user);

        when(habitRepository.save(any(Habit.class))).thenReturn(savedHabit);

        HabitResponse result = habitService.createHabit(request);

        assertNotNull(result);

        assertEquals("Morning Run", result.getTitle());

        verify(habitRepository, times(1)).save(any(Habit.class));
    }

    @Test
    void shouldMarkHabitAsCompleteSuccessfully() {

        User user = new User();
        user.setId(1L);

        Habit habit = new Habit();
        habit.setId(10L);
        habit.setTitle("Morning Run");
        habit.setCompleted(false);

        when(authenticationService.getCurrentUser())
                .thenReturn(user);

        when(habitRepository.findByIdAndUser(10L, user))
                .thenReturn(Optional.of(habit));

        when(habitRepository.save(any(Habit.class)))
                .thenReturn(habit);

        HabitResponse result =
                habitService.markHabitComplete(10L);

        assertNotNull(result);
        assertTrue(result.isCompleted());

        verify(habitRepository)
                .save(habit);
    }

    @Test
    void shouldThrowExceptionWhenHabitNotFound() {

        User user = new User();
        user.setId(1L);

        when(authenticationService.getCurrentUser())
                .thenReturn(user);

        when(habitRepository.findByIdAndUser(99L, user))
                .thenReturn(Optional.empty());

        assertThrows(
                HabitNotFoundException.class,
                () -> habitService.markHabitComplete(99L)
        );

        verify(habitRepository, never())
                .save(any(Habit.class));
    }

    @Test
    void shouldDeleteHabitSuccessfully() {

        User user = new User();
        user.setId(1L);

        Habit habit = new Habit();
        habit.setId(10L);

        when(authenticationService.getCurrentUser())
                .thenReturn(user);

        when(habitRepository.findByIdAndUser(10L, user))
                .thenReturn(Optional.of(habit));

        String result =
                habitService.deleteHabit(10L);

        assertEquals(
                "Habit deleted successfully",
                result
        );

        verify(habitRepository)
                .delete(habit);
    }

    @Test
    void shouldThrowExceptionWhenDeletingMissingHabit() {

        User user = new User();
        user.setId(1L);

        when(authenticationService.getCurrentUser())
                .thenReturn(user);

        when(habitRepository.findByIdAndUser(99L, user))
                .thenReturn(Optional.empty());

        assertThrows(
                HabitNotFoundException.class,
                () -> habitService.deleteHabit(99L)
        );

        verify(habitRepository, never())
                .delete(any(Habit.class));
    }

    @Test
    void shouldReturnCorrectHabitStats() {

        User user = new User();
        user.setId(1L);

        when(authenticationService.getCurrentUser())
                .thenReturn(user);

        when(habitRepository.countByUser(user))
                .thenReturn(10L);

        when(habitRepository.countByUserAndCompleted(user, true))
                .thenReturn(6L);

        HabitStatsResponse result =
                habitService.getHabitStats();

        assertNotNull(result);

        assertEquals(10, result.getTotalHabits());
        assertEquals(6, result.getCompletedHabits());
        assertEquals(4, result.getPendingHabits());
        assertEquals(60.0, result.getCompletionRate());

        verify(habitRepository)
                .countByUser(user);

        verify(habitRepository)
                .countByUserAndCompleted(user, true);
    }

    @Test
    void shouldReturnZeroStatsWhenNoHabitsExist() {

        User user = new User();
        user.setId(1L);

        when(authenticationService.getCurrentUser())
                .thenReturn(user);

        when(habitRepository.countByUser(user))
                .thenReturn(0L);

        when(habitRepository.countByUserAndCompleted(user, true))
                .thenReturn(0L);

        HabitStatsResponse result =
                habitService.getHabitStats();

        assertEquals(0, result.getTotalHabits());
        assertEquals(0, result.getCompletedHabits());
        assertEquals(0, result.getPendingHabits());
        assertEquals(0.0, result.getCompletionRate());
    }

    @Test
    void shouldUpdateHabitSuccessfully() {

        Long habitId = 1L;

        UpdateHabitRequest request = new UpdateHabitRequest();
        request.setTitle("Evening Run");
        request.setDescription("Run 5km");

        User user = new User();
        user.setId(1L);

        Habit habit = new Habit();
        habit.setId(habitId);
        habit.setTitle("Morning Run");
        habit.setDescription("Run 3km");
        habit.setUser(user);

        when(authenticationService.getCurrentUser())
                .thenReturn(user);

        when(habitRepository.findByIdAndUser(habitId, user))
                .thenReturn(Optional.of(habit));

        when(habitRepository.save(any(Habit.class)))
                .thenReturn(habit);

        HabitResponse result =
                habitService.updateHabit(habitId, request);

        assertNotNull(result);
        assertEquals("Evening Run", result.getTitle());

        verify(habitRepository, times(1))
                .save(any(Habit.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingMissingHabit() {

        Long habitId = 99L;

        UpdateHabitRequest request =
                new UpdateHabitRequest();

        User user = new User();

        when(authenticationService.getCurrentUser())
                .thenReturn(user);

        when(habitRepository.findByIdAndUser(habitId, user))
                .thenReturn(Optional.empty());

        assertThrows(
                HabitNotFoundException.class,
                () -> habitService.updateHabit(
                        habitId,
                        request
                )
        );

        verify(habitRepository, never())
                .save(any());
    }


}
