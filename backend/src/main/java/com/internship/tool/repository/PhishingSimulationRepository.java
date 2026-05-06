package com.internship.tool.repository;

import com.internship.tool.entity.PhishingSimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for PhishingSimulation entity.
 * Provides standard CRUD operations and custom search methods.
 */
@Repository
public interface PhishingSimulationRepository extends JpaRepository<PhishingSimulation, Long> {

    // Custom query method to find simulations by their current status
    List<PhishingSimulation> findByStatus(String status);

    // Custom query method to filter simulations by target department
    List<PhishingSimulation> findByTargetDepartment(String department);
}