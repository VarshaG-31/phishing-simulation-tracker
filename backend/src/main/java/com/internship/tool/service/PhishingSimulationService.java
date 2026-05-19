package com.internship.tool.service;

import com.internship.tool.entity.PhishingSimulation;
import org.springframework.data.domain.Page; // Day 11 Import
import java.util.List;

public interface PhishingSimulationService {

    List<PhishingSimulation> getAllSimulations();

    PhishingSimulation getSimulationById(Long id);

    PhishingSimulation createSimulation(PhishingSimulation simulation);

    PhishingSimulation updateSimulationStatus(Long id, String status);

    // Day 11 Update: The exact matching signature contract to fix the compiler error
    Page<PhishingSimulation> getPaginatedAndFilteredSimulations(
            String status,
            String department,
            int page,
            int size,
            String sortBy,
            String sortDir
    );
}