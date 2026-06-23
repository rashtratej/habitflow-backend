package com.rashtratej.habitTrackerVersion1.repository;

import com.rashtratej.habitTrackerVersion1.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    }
