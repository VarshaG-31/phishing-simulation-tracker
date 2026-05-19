package com.internship.tool.controller;

import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.service.PhishingSimulationService;
import jakarta.validation.Valid; // Required for Day 9 Input Validation
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Day 8/9 Update: Fetch a specific simulation context safely
    @GetMapping("/{id}")
    public ResponseEntity<PhishingSimulation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSimulationById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // RBAC: Only Admin can create
    public ResponseEntity<PhishingSimulation> create(@Valid @RequestBody PhishingSimulation simulation) {
        // Day 9 Update: Added @Valid to intercept malformed request data payloads
        return new ResponseEntity<>(service.createSimulation(simulation), HttpStatus.CREATED);
    }

    // Day 8/9 Update: Expose state transitions for active tracking runs
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PhishingSimulation> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(service.updateSimulationStatus(id, status));
    }
}