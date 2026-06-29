package com.rashtratej.habitTrackerVersion1.exception;

public class InvalidSortFieldException
        extends RuntimeException {

    public InvalidSortFieldException(
            String message
    ) {
        super(message);
    }
}