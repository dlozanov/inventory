package com.example.inventory.service;

public interface LogService {

    void save(String message);

    void cleanAllLogs();
}
