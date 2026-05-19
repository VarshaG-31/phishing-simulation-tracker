package com.internship.tool.service.impl;

import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.exception.ResourceNotFoundException; // Day 8 Exception Link
import com.internship.tool.repository.PhishingSimulationRepository;
import com.internship.tool.service.PhishingSimulationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;       // Day 11 Import
import org.springframework.data.domain.PageRequest;// Day 11 Import
import org.springframework.data.domain.Pageable;   // Day 11 Import
import org.springframework.data.domain.Sort;       // Day 11 Import
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PhishingSimulationServiceImpl implements PhishingSimulationService {

    private final PhishingSimulationRepository repository;
    private final NotificationService notificationService; // Recognizes the file next to it

    // Constructor matching both required dependencies
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
        // Day 8 Update: Replaced generic RuntimeException with your custom ResourceNotFoundException!
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Simulation not found with id: " + id));
    }

    @Override
    @CacheEvict(value = "simulations", allEntries = true)
    public PhishingSimulation createSimulation(PhishingSimulation simulation) {
        PhishingSimulation savedSimulation = repository.save(simulation);

        // Triggers email broadcast using templateName
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

        // Triggers email update status broadcast using templateName
        notificationService.sendSimulationReminder(
                "security-coordinator@vtu-internship.local",
                updatedSimulation.getTemplateName() != null ? updatedSimulation.getTemplateName() : "Campaign Status Change",
                updatedSimulation.getStatus()
        );

        return updatedSimulation;
    }

    // --- Day 11 Pagination and Sorting Implementation ---
    @Override
    public Page<PhishingSimulation> getPaginatedAndFilteredSimulations(
            String status, String department, int page, int size, String sortBy, String sortDir) {

        // 1. Establish dynamic sorting direction based on user choice (ASC vs DESC)
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // 2. Build the Pageable criteria tracking object (Spring uses 0-indexed pages)
        Pageable pageable = PageRequest.of(page, size, sort);

        // 3. Execute conditional filtering depending on what parameters are passed from the API
        if (status != null && department != null) {
            return repository.findByStatusAndTargetDepartment(status, department, pageable);
        } else if (status != null) {
            return repository.findByStatus(status, pageable);
        } else if (department != null) {
            return repository.findByTargetDepartment(department, pageable);
        }

        // Default fallback: If no filters are provided, return everything paginated
        return repository.findAll(pageable);
    }
}