package com.rashtratej.habitTrackerVersion1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "habits")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private int streak;

    private LocalDate lastCompletedDate;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(
            mappedBy = "habit",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<HabitCompletion> completions;


    public Habit() {
    }

    public boolean isCompleted() {
        return completed;
    }

    // getters

    public int getStreak() {
        return streak;
    }

    public LocalDate getLastCompletedDate() {
        return lastCompletedDate;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



 // setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public void setLastCompletedDate(LocalDate lastCompletedDate) {
        this.lastCompletedDate = lastCompletedDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

}