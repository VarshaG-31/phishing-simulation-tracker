package com.internship.tool.repository;

import com.internship.tool.entity.PhishingClickLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhishingClickLogRepository extends JpaRepository<PhishingClickLog, Long> {
    List<PhishingClickLog> findBySimulationId(Long simulationId);
    long countByEmployeeEmail(String employeeEmail);
}
