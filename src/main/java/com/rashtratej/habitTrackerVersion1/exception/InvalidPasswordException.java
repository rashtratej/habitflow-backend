package com.rashtratej.habitTrackerVersion1.exception;

public class InvalidPasswordException
        extends RuntimeException {

    public InvalidPasswordException(
            String message
    ) {
        super(message);
    }
}