package com.rashtratej.habitTrackerVersion1.repository;

import com.rashtratej.habitTrackerVersion1.entity.Habit;
import com.rashtratej.habitTrackerVersion1.entity.HabitCompletion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface HabitCompletionRepository
        extends JpaRepository<HabitCompletion, Long> {

    Optional<HabitCompletion>
    findByHabitAndCompletionDate(Habit habit, LocalDate date);

    List<HabitCompletion>
    findByHabitOrderByCompletionDateDesc(Habit habit);
}