package com.rashtratej.habitTrackerVersion1.service;

import com.rashtratej.habitTrackerVersion1.dto.CreateHabitRequest;
import com.rashtratej.habitTrackerVersion1.entity.Habit;
import com.rashtratej.habitTrackerVersion1.entity.User;
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

    public HabitService(
            HabitRepository habitRepository,
            UserRepository userRepository
    ) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }


    public Habit createHabit(CreateHabitRequest request) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();
        User user =
                userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
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

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        return habitRepository.findByUser(user);
    }

    public Habit markHabitComplete(Long habitId) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();


        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        Habit habit =
                habitRepository
                        .findByIdAndUser(
                                habitId,
                                user
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Habit not found"
                                )
                        );


        habit.setCompleted(true);

        return habitRepository.save(habit);
    }
}