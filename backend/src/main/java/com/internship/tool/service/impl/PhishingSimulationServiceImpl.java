package com.internship.tool.service.impl;

import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.repository.PhishingSimulationRepository;
import com.internship.tool.service.PhishingSimulationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PhishingSimulationServiceImpl implements PhishingSimulationService {

    private final PhishingSimulationRepository repository;
    private final NotificationService notificationService; // Added for Day 7 email capabilities

    // Updated constructor to inject both dependencies
    public PhishingSimulationServiceImpl(PhishingSimulationRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    @Override
    @Cacheable(value = "simulations", key = "'allSimulations'")
    public List<PhishingSimulation> getAllSimulations() {
        return repository.findAll();
    }

    @Override
    @Cacheable(value = "simulations", key = "#id")
    public PhishingSimulation getSimulationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Simulation not found with id: " + id));
    }

    @Override
    @CacheEvict(value = "simulations", allEntries = true)
    public PhishingSimulation createSimulation(PhishingSimulation simulation) {
        PhishingSimulation savedSimulation = repository.save(simulation);

        // Day 7 Feature: Automatically alert the security administrator immediately when a new drill is launched
        if ("PENDING".equalsIgnoreCase(savedSimulation.getStatus()) || "ACTIVE".equalsIgnoreCase(savedSimulation.getStatus())) {
            notificationService.sendSimulationReminder(
                    "security-coordinator@vtu-internship.local",
                    savedSimulation.getTemplateName() != null ? savedSimulation.getTemplateName() : "New Campaign Alert",
                    savedSimulation.getStatus()
            );
        }

        return savedSimulation;
    }

    @Override
    @CacheEvict(value = "simulations", allEntries = true)
    public PhishingSimulation updateSimulationStatus(Long id, String status) {
        PhishingSimulation simulation = getSimulationById(id);
        simulation.setStatus(status);
        PhishingSimulation updatedSimulation = repository.save(simulation);

        // Day 7 Feature: Send an update notification when a simulation status transitions
        notificationService.sendSimulationReminder(
                "security-coordinator@vtu-internship.local",
                updatedSimulation.getTemplateName() != null ? updatedSimulation.getTemplateName() : "Campaign Status Change",
                updatedSimulation.getStatus()
        );

        return updatedSimulation;
    }
}