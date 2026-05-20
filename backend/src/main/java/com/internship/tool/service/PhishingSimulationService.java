package com.internship.tool.service;
import com.internship.tool.service.PhishingSimulationService;
import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.projection.SimulationSummary;
import org.springframework.data.domain.Page;
import java.util.List;

public interface PhishingSimulationService {

    List<PhishingSimulation> getAllSimulations();

    PhishingSimulation getSimulationById(Long id);

    PhishingSimulation createSimulation(PhishingSimulation simulation);

    PhishingSimulation updateSimulationStatus(Long id, String status);

    Page<PhishingSimulation> getPaginatedAndFilteredSimulations(
            String status, String department, int page, int size, String sortBy, String sortDir);

    List<SimulationSummary> getSimulationSummariesByStatus(String status);
}