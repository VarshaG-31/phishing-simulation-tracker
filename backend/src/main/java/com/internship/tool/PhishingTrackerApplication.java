package com.internship.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;       // Day 12 Import
import org.springframework.scheduling.annotation.EnableScheduling;  // Day 12 Import

@SpringBootApplication
@EnableAsync        // Activates background thread execution (@Async)
@EnableScheduling   // Activates time-based recurring tasks (@Scheduled)
public class PhishingTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhishingTrackerApplication.class, args);
    }
}