package com.internship.tool.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "phishing_simulations")
public class PhishingSimulation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Simulation name is mandatory")
    @Size(min = 3, max = 100, message = "Simulation name must be between 3 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Template name is mandatory")
    @Column(nullable = false)
    private String templateName;

    @NotBlank(message = "Target department is mandatory")
    @Column(nullable = false)
    private String targetDepartment;

    @NotBlank(message = "Status cannot be blank")
    @Column(nullable = false)
    private String status;

    // --- EXPLICIT GETTERS AND SETTERS (Fixes the Build Errors) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTargetDepartment() {
        return targetDepartment;
    }

    public void setTargetDepartment(String targetDepartment) {
        this.targetDepartment = targetDepartment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}