package com.rashtratej.habitTrackerVersion1.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "habit_completions")
public class HabitCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate completionDate;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    public HabitCompletion() {}

    public Long getId() {
        return id;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }
}