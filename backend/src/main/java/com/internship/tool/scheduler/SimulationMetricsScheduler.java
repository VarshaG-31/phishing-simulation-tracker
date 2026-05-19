package com.internship.tool.scheduler;

import com.internship.tool.repository.PhishingSimulationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimulationMetricsScheduler {

    private final PhishingSimulationRepository repository;

    // Constructor Injection to securely interact with the database
    public SimulationMetricsScheduler(PhishingSimulationRepository repository) {
        this.repository = repository;
    }

    // Day 12 Cron Task: Automatically executes every 60 seconds (60000 ms)
    @Scheduled(fixedRate = 60000)
    public void reportSystemMetrics() {
        long totalSimulations = repository.count();

        System.out.println("\n--- CRON SYSTEM REPORT ---");
        System.out.println("LOG: [Thread - " + Thread.currentThread().getName() + "] Automated background metrics check triggered.");
        System.out.println("Current tracking simulation count in database: " + totalSimulations);
        System.out.println("--------------------------\n");
    }
}