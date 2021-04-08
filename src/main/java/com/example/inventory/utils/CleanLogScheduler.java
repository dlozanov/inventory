package com.example.inventory.utils;

import com.example.inventory.service.LogService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanLogScheduler {

    private final LogService logService;

    public CleanLogScheduler(LogService logService) {
        this.logService = logService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanLogEveryNight(){
        logService.cleanAllLogs();
    }

}
