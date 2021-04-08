package com.example.inventory.service.impl;

import com.example.inventory.model.entity.LogEntity;
import com.example.inventory.repository.LogRepositoy;
import com.example.inventory.service.LogService;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepositoy logRepositoy;

    public LogServiceImpl(LogRepositoy logRepositoy) {
        this.logRepositoy = logRepositoy;
    }

    @Override
    public void save(String message) {
        LogEntity logEntity = new LogEntity();
        logEntity.setMessage(message);
        logRepositoy.save(logEntity);
    }

    @Override
    public void cleanAllLogs() {
        logRepositoy.deleteAll();
    }
}
