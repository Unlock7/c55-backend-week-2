package com.hyfacademy.exception;

public class InvalidProgressException extends EnrolmentException {
    private int attemptedValue;
    public InvalidProgressException(int value) {
        super("Progress must be between 0 and 100, but received: " + value);
        this.attemptedValue = value;
    }

    public int getAttemptedValue() {
        return attemptedValue;
    }
}
