package com.internship.tool.repository;

import com.internship.tool.entity.PhishingSimulation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhishingSimulationRepository extends JpaRepository<PhishingSimulation, Long> {

    // Day 11 Optimization: Multi-criteria advanced filtering with native pagination
    Page<PhishingSimulation> findByStatusAndTargetDepartment(String status, String targetDepartment, Pageable pageable);

    Page<PhishingSimulation> findByStatus(String status, Pageable pageable);

    Page<PhishingSimulation> findByTargetDepartment(String targetDepartment, Pageable pageable);
}