package com.rashtratej.habitTrackerVersion1.exception;

public class DuplicateEmailException
        extends RuntimeException {

    public DuplicateEmailException(
            String message
    ) {
        super(message);
    }
}