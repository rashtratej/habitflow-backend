package com.rashtratej.habitTrackerVersion1.dto;

public class HabitStatsResponse {

    private int totalHabits;
    private int completedHabits;
    private int pendingHabits;
    private double completionRate;

    public HabitStatsResponse(
            int totalHabits,
            int completedHabits,
            int pendingHabits,
            double completionRate
    ) {
        this.totalHabits = totalHabits;
        this.completedHabits = completedHabits;
        this.pendingHabits = pendingHabits;
        this.completionRate = completionRate;
    }

    public HabitStatsResponse() {

    }

    public int getTotalHabits() {
        return totalHabits;
    }

    public int getCompletedHabits() {
        return completedHabits;
    }

    public int getPendingHabits() {
        return pendingHabits;
    }

    public double getCompletionRate() {
        return completionRate;
    }
}