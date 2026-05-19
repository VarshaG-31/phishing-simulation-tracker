package com.internship.tool.controller;

import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.service.PhishingSimulationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Import this!
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/simulations")
public class PhishingSimulationController {

    private final PhishingSimulationService service;

    public PhishingSimulationController(PhishingSimulationService service) {
        this.service = service;
    }

    @GetMapping
    public List<PhishingSimulation> getAll() {
        return service.getAllSimulations();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // RBAC: Only Admin can create
    public ResponseEntity<PhishingSimulation> create(@RequestBody PhishingSimulation simulation) {
        return ResponseEntity.ok(service.createSimulation(simulation));
    }
}