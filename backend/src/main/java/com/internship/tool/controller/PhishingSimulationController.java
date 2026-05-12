package com.internship.tool.controller;

import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.service.PhishingSimulationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/simulations")
@CrossOrigin(origins = "*") // Allows the frontend to talk to the backend
public class PhishingSimulationController {

    private final PhishingSimulationService service;

    // Constructor Injection
    public PhishingSimulationController(PhishingSimulationService service) {
        this.service = service;
    }

    // 1. Create a new simulation
    @PostMapping
    public ResponseEntity<PhishingSimulation> createSimulation(@RequestBody PhishingSimulation simulation) {
        return new ResponseEntity<>(service.createSimulation(simulation), HttpStatus.CREATED);
    }

    // 2. Get all simulations
    @GetMapping
    public List<PhishingSimulation> getAllSimulations() {
        return service.getAllSimulations();
    }

    // 3. Get simulation by ID
    @GetMapping("/{id}")
    public ResponseEntity<PhishingSimulation> getSimulationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSimulationById(id));
    }

    // 4. Update simulation status
    @PutMapping("/{id}/status")
    public ResponseEntity<PhishingSimulation> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(service.updateSimulationStatus(id, status));
    }
}