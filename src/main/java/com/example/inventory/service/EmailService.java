package com.example.inventory.service;

public interface EmailService {
    void sendMessage(String to, String subject, String text);
}
