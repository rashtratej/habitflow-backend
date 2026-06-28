package com.rashtratej.habitTrackerVersion1.dto;

import java.util.List;

public class PaginatedHabitResponse {

    private List<HabitResponse> habits;
    private int currentPage;
    private int totalPages;
    private long totalItems;

    public PaginatedHabitResponse(
            List<HabitResponse> habits,
            int currentPage,
            int totalPages,
            long totalItems
    ) {
        this.habits = habits;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public List<HabitResponse> getHabits() {
        return habits;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }
}