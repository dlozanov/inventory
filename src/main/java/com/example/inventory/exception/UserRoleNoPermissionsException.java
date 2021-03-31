package com.example.inventory.exception;

public class UserRoleNoPermissionsException extends RuntimeException{
    public UserRoleNoPermissionsException(String message) {
        super(message);
    }
}
