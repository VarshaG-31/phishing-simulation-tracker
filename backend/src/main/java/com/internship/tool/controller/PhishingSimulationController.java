package com.internship.tool.controller;

import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.service.PhishingSimulationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page; // Day 11 Import
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/simulations")
public class PhishingSimulationController {

    private final PhishingSimulationService service;

    public PhishingSimulationController(PhishingSimulationService service) {
        this.service = service;
    }

    // Day 11 Endpoint: Fetch simulations with Pagination, Sorting, and Dynamic Filtering
    @GetMapping
    public ResponseEntity<Page<PhishingSimulation>> getSimulations(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<PhishingSimulation> result = service.getPaginatedAndFilteredSimulations(
                status, department, page, size, sortBy, sortDir
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhishingSimulation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSimulationById(id));
    }

    @PostMapping
    public ResponseEntity<PhishingSimulation> create(@Valid @RequestBody PhishingSimulation simulation) {
        return new ResponseEntity<>(service.createSimulation(simulation), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PhishingSimulation> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(service.updateSimulationStatus(id, status));
    }
}