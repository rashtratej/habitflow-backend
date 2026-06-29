package com.rashtratej.habitTrackerVersion1.service;

import com.rashtratej.habitTrackerVersion1.dto.*;
import com.rashtratej.habitTrackerVersion1.entity.Habit;
import com.rashtratej.habitTrackerVersion1.entity.User;
import com.rashtratej.habitTrackerVersion1.exception.HabitNotFoundException;
import com.rashtratej.habitTrackerVersion1.exception.InvalidSortFieldException;
import com.rashtratej.habitTrackerVersion1.repository.HabitRepository;
import com.rashtratej.habitTrackerVersion1.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public HabitService(HabitRepository habitRepository, UserRepository userRepository, AuthenticationService authenticationService) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    public HabitResponse createHabit(CreateHabitRequest request) {


        User user = authenticationService.getCurrentUser();
        Habit habit = new Habit();

        habit.setTitle(request.getTitle());

        habit.setDescription(request.getDescription());

        habit.setCompleted(false);

        habit.setStreak(0);

        habit.setLastCompletedDate(null);

        habit.setCreatedAt(LocalDateTime.now());

        habit.setUser(user);


        return mapToHabitResponse(
                habitRepository.save(habit)
        );
    }

    public PaginatedHabitResponse getMyHabits(int page, int size, String sortBy, String direction) {

        User user = authenticationService.getCurrentUser();

        if (!sortBy.equals("createdAt")
                &&
                !sortBy.equals("title")
                &&
                !sortBy.equals("completed")) {

            throw new InvalidSortFieldException("Invalid sort field");
        }


        Sort sort;
        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Habit> habitPage = habitRepository.findByUser(user, pageable);

        List<HabitResponse> habits = habitPage.getContent()
                        .stream()
                        .map(this::mapToHabitResponse)
                        .toList();

        return new PaginatedHabitResponse(
                habits,
                habitPage.getNumber(),
                habitPage.getTotalPages(),
                habitPage.getTotalElements()
        );
    }

    public HabitResponse markHabitComplete(Long habitId) {

        User user = authenticationService.getCurrentUser();;

        Habit habit = habitRepository.findByIdAndUser(habitId, user)
                        .orElseThrow(() -> new HabitNotFoundException("Habit not found"));


//        habit.setCompleted(true);

        LocalDate today = LocalDate.now();

        if (habit.getLastCompletedDate() == null) {
            habit.setStreak(1);
        }
        else if (habit.getLastCompletedDate().equals(today)) {
            // already completed today
        }
        else if (habit.getLastCompletedDate().equals(today.minusDays(1))) {
            habit.setStreak(habit.getStreak() + 1);
        }
        else {
            habit.setStreak(1);
        }

        habit.setLastCompletedDate(today);
        habit.setCompleted(true);

        return mapToHabitResponse(habitRepository.save(habit));
    }

    public HabitStatsResponse getHabitStats() {

        User user = authenticationService.getCurrentUser();;

        int total =
                (int) habitRepository
                        .countByUser(user);

        int completed =
                (int) habitRepository
                        .countByUserAndCompleted(
                                user,
                                true
                        );

        int pending =
                total - completed;

        double rate = 0.0;

        if (total > 0) {
            rate =
                    ((double) completed
                            / total) * 100;
        }

        return new HabitStatsResponse(
                total,
                completed,
                pending,
                rate
        );
    }

    public HabitResponse updateHabit(Long habitId, UpdateHabitRequest request) {

        User user = authenticationService.getCurrentUser();;
        Habit habit =
                habitRepository
                        .findByIdAndUser(
                                habitId,
                                user
                        )
                        .orElseThrow(() ->
                                new HabitNotFoundException(
                                        "Habit not found"
                                )
                        );

        habit.setTitle(
                request.getTitle()
        );

        habit.setDescription(
                request.getDescription()
        );

        return mapToHabitResponse(habitRepository.save(habit));
    }

    public String deleteHabit(Long habitId) {

        User user =
                authenticationService.getCurrentUser();;

        Habit habit =
                habitRepository
                        .findByIdAndUser(
                                habitId,
                                user
                        )
                        .orElseThrow(() ->
                                new HabitNotFoundException(
                                        "Habit not found"
                                )
                        );

        habitRepository.delete(habit);

        return "Habit deleted successfully";
    }

    private HabitResponse mapToHabitResponse(Habit habit) {
        return new HabitResponse(
                habit.getId(),
                habit.getTitle(),
                habit.getDescription(),
                habit.isCompleted(),
                habit.getCreatedAt(),
                habit.getStreak()
        );
    }

}