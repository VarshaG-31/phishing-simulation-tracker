package com.internship.tool.service.impl;

// ⚠️ Missing core imports causing your errors:
import com.internship.tool.service.PhishingSimulationService;
import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.projection.SimulationSummary;
import com.internship.tool.repository.PhishingSimulationRepository;

// Spring ecosystem imports:
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PhishingSimulationServiceImpl implements PhishingSimulationService {

    @Autowired
    private PhishingSimulationRepository repository;

    @Override
    public List<PhishingSimulation> getAllSimulations() {
        return repository.findAll();
    }

    @Override
    public PhishingSimulation getSimulationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Simulation not found with id: " + id));
    }

    @Override
    public PhishingSimulation createSimulation(PhishingSimulation simulation) {
        return repository.save(simulation);
    }

    @Override
    public PhishingSimulation updateSimulationStatus(Long id, String status) {
        PhishingSimulation simulation = getSimulationById(id);
        simulation.setStatus(status);
        return repository.save(simulation);
    }

    @Override
    public Page<PhishingSimulation> getPaginatedAndFilteredSimulations(
            String status, String department, int page, int size, String sortBy, String sortDir) {

        // Handle sorting direction dynamically
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Advanced filtering logic based on what parameters are provided
        if (status != null && department != null) {
            return repository.findByStatusAndTargetDepartment(status, department, pageable);
        } else if (status != null) {
            return repository.findByStatus(status, pageable);
        } else if (department != null) {
            return repository.findByTargetDepartment(department, pageable);
        } else {
            return repository.findAll(pageable);
        }
    }

    @Override
    public List<SimulationSummary> getSimulationSummariesByStatus(String status) {
        // Leverages your JPA projection interface
        return repository.findSummaryByStatus(status);
    }
}