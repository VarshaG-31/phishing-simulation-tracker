package com.internship.tool.controller;

import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.repository.PhishingSimulationRepository;
import com.internship.tool.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulations")
public class PhishingSimulationController {

    @Autowired
    private PhishingSimulationRepository simulationRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/launch")
    public ResponseEntity<PhishingSimulation> launchSimulation(
            @RequestParam String name,
            @RequestParam String targetDepartment,
            @RequestParam String templateName,
            @RequestParam String targetEmail) {
        
        PhishingSimulation simulation = new PhishingSimulation();
        simulation.setName(name);
        simulation.setTargetDepartment(targetDepartment);
        simulation.setTemplateName(templateName);
        simulation.setStatus("ACTIVE");

        PhishingSimulation savedSimulation = simulationRepository.save(simulation);

        try {
            emailService.sendSimulationEmail(targetEmail, savedSimulation.getId(), templateName);
        } catch (Exception e) {
            savedSimulation.setStatus("EMAIL_FAILED");
            simulationRepository.save(savedSimulation);
            return ResponseEntity.status(500).body(savedSimulation);
        }

        return ResponseEntity.ok(savedSimulation);
    }
}