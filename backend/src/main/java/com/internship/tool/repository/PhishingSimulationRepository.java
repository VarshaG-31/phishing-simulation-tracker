package com.internship.tool.repository;

import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.projection.SimulationSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhishingSimulationRepository extends JpaRepository<PhishingSimulation, Long> {

    // 💥 THIS IS THE EXACT METHOD LINE 73 IS LOOKING FOR:
    List<SimulationSummary> findSummaryByStatus(String status);

    // These three supporting methods are required for your pagination logic:
    Page<PhishingSimulation> findByStatusAndTargetDepartment(String status, String department, Pageable pageable);
    Page<PhishingSimulation> findByStatus(String status, Pageable pageable);
    Page<PhishingSimulation> findByTargetDepartment(String department, Pageable pageable);
}