package com.internship.tool.service;

import com.internship.tool.entity.PhishingSimulation;
import java.util.List;

public interface PhishingSimulationService {
    PhishingSimulation createSimulation(PhishingSimulation simulation);
    List<PhishingSimulation> getAllSimulations();
    PhishingSimulation getSimulationById(Long id);
    PhishingSimulation updateSimulationStatus(Long id, String status);
}