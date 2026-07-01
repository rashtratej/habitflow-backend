package com.rashtratej.habitTrackerVersion1.repository;

import com.rashtratej.habitTrackerVersion1.entity.Habit;
import com.rashtratej.habitTrackerVersion1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long> {


    //List<Habit> findByUser(User user);
    Page<Habit> findByUser(User user, Pageable pageable);

    Optional<Habit> findByIdAndUser(Long id, User user);

    long countByUser(User user);

    long countByUserAndCompleted(User user, boolean completed);

    @Modifying
    @Transactional
    @Query("UPDATE Habit h SET h.completed = false")
    void resetAllHabits();


    }
