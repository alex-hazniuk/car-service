package org.example.exception;

public class InappropriateStatusException extends RuntimeException {
    public InappropriateStatusException(String message) {
        super(message);
    }
}
