package com.example.online_shop.exception;

public class InsufficientPaymentException extends RuntimeException{
    public InsufficientPaymentException(String message) {
        super(message);
    }
}
