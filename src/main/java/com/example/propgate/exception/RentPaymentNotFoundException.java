package com.example.propgate.exception;

public class RentPaymentNotFoundException extends RuntimeException {
    public RentPaymentNotFoundException(String message) {
        super(message);
    }
}