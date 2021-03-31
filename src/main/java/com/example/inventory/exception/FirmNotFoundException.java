package com.example.inventory.exception;

public class FirmNotFoundException extends RuntimeException{
    public FirmNotFoundException(String message) {
        super(message);
    }
}
