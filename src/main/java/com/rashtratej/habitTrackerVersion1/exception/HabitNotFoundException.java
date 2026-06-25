package com.rashtratej.habitTrackerVersion1.exception;

public class HabitNotFoundException
        extends RuntimeException {

    public HabitNotFoundException(
            String message
    ) {
        super(message);
    }
}