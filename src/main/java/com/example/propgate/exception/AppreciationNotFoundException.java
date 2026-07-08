package com.example.propgate.exception;

public class AppreciationNotFoundException extends RuntimeException {
    public AppreciationNotFoundException(String message) {
        super(message);
    }
}
