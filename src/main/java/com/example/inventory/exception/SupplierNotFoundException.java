package com.example.inventory.exception;

public class SupplierNotFoundException extends RuntimeException{
    public SupplierNotFoundException(String message) {
        super(message);
    }
}
