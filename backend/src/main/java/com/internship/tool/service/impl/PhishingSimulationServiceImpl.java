package com.internship.tool.service.impl;

import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.PhishingSimulationRepository;
import com.internship.tool.service.PhishingSimulationService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PhishingSimulationServiceImpl implements PhishingSimulationService {

    private final PhishingSimulationRepository repository;

    // Manual constructor - This fixes the initialization error
    public PhishingSimulationServiceImpl(PhishingSimulationRepository repository) {
        this.repository = repository;
    }

    @Override
    public PhishingSimulation createSimulation(PhishingSimulation simulation) {
        simulation.setStatus("PENDING");
        return repository.save(simulation);
    }

    @Override
    public List<PhishingSimulation> getAllSimulations() {
        return repository.findAll();
    }

    @Override
    public PhishingSimulation getSimulationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Simulation not found with id: " + id));
    }

    @Override
    public PhishingSimulation updateSimulationStatus(Long id, String status) {
        PhishingSimulation simulation = getSimulationById(id);
        simulation.setStatus(status);
        return repository.save(simulation);
    }
}