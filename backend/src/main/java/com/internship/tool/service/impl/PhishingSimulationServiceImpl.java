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

    public PhishingSimulationServiceImpl(PhishingSimulationRepository repository) {
        this.repository = repository;
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
        return repository.save(simulation);
    }

    // --- ADDING THIS TO FIX THE FINAL RED ERROR ---
    @Override
    @CacheEvict(value = "simulations", allEntries = true)
    public PhishingSimulation updateSimulationStatus(Long id, String status) {
        PhishingSimulation simulation = getSimulationById(id);
        simulation.setStatus(status);
        return repository.save(simulation);
    }
}