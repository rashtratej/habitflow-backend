package com.rashtratej.habitTrackerVersion1.dto;

import java.time.LocalDateTime;

public class HabitResponse {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private int streak;

    public HabitResponse(
            Long id,
            String title,
            String description,
            boolean completed,
            LocalDateTime createdAt,
            int streak

    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.streak = streak;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getStreak() {
        return streak;
    }

}