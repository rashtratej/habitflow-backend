package com.rashtratej.habitTrackerVersion1.service;

import com.rashtratej.habitTrackerVersion1.dto.CreateHabitRequest;
import com.rashtratej.habitTrackerVersion1.dto.HabitResponse;
import com.rashtratej.habitTrackerVersion1.dto.HabitStatsResponse;
import com.rashtratej.habitTrackerVersion1.dto.UpdateHabitRequest;
import com.rashtratej.habitTrackerVersion1.entity.Habit;
import com.rashtratej.habitTrackerVersion1.entity.User;
import com.rashtratej.habitTrackerVersion1.exception.HabitNotFoundException;
import com.rashtratej.habitTrackerVersion1.exception.UserNotFoundException;
import com.rashtratej.habitTrackerVersion1.repository.HabitRepository;
import com.rashtratej.habitTrackerVersion1.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public Habit createHabit(CreateHabitRequest request) {

        User user = authenticationService.getCurrentUser();;
        Habit habit = new Habit();

        habit.setTitle(request.getTitle());

        habit.setDescription(
                request.getDescription()
        );

        habit.setCompleted(false);

        habit.setCreatedAt(
                LocalDateTime.now()
        );

        habit.setUser(user);


        return habitRepository.save(habit);
    }

    public List<Habit> getMyHabits() {

        User user = authenticationService.getCurrentUser();;

        return habitRepository.findByUser(user);
    }

    public Habit markHabitComplete(Long habitId) {

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


        habit.setCompleted(true);

        return habitRepository.save(habit);
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

    public Habit updateHabit(Long habitId, UpdateHabitRequest request) {

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

        return habitRepository.save(habit);
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

}