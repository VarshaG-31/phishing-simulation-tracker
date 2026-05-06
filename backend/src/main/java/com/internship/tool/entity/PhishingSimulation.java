package com.internship.tool.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * Entity representing a Phishing Simulation record.
 * Maps to the 'phishing_simulations' table in PostgreSQL.
 */
@Entity
@Table(name = "phishing_simulations")
@Data
@EntityListeners(AuditingEntityListener.class)
public class PhishingSimulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "target_department", nullable = false)
    private String targetDepartment;

    @Column(nullable = false)
    private String status; // Initial status should be 'PENDING'

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}