package com.internship.tool;

import com.internship.tool.entity.PhishingSimulation;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.PhishingSimulationRepository;
import com.internship.tool.service.impl.NotificationService;
import com.internship.tool.service.impl.PhishingSimulationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhishingSimulationServiceTest {

    @Mock
    private PhishingSimulationRepository repository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private PhishingSimulationServiceImpl service;

    private PhishingSimulation sampleSimulation;

    @BeforeEach
    void setUp() {
        sampleSimulation = new PhishingSimulation();
        sampleSimulation.setId(1L);
        sampleSimulation.setName("Executive Phish Target Drill");
        sampleSimulation.setTemplateName("Urgent Password Reset Template");
        sampleSimulation.setTargetDepartment("Human Resources");
        sampleSimulation.setStatus("PENDING");
    }

    @Test
    void testGetAllSimulations_ReturnsList() {
        when(repository.findAll()).thenReturn(List.of(sampleSimulation));
        List<PhishingSimulation> result = service.getAllSimulations();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetAllSimulations_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<PhishingSimulation> result = service.getAllSimulations();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetSimulationById_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleSimulation));
        PhishingSimulation result = service.getSimulationById(1L);
        assertNotNull(result);
        assertEquals("Executive Phish Target Drill", result.getName());
    }

    @Test
    void testGetSimulationById_ThrowsResourceNotFoundException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getSimulationById(99L));
    }

    @Test
    void testCreateSimulation_TriggersNotificationForActiveStatus() {
        when(repository.save(any(PhishingSimulation.class))).thenReturn(sampleSimulation);
        PhishingSimulation created = service.createSimulation(sampleSimulation);
        assertNotNull(created);
        verify(notificationService, times(1)).sendSimulationReminder(anyString(), anyString(), anyString());
    }

    @Test
    void testUpdateSimulationStatus_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleSimulation));
        when(repository.save(any(PhishingSimulation.class))).thenReturn(sampleSimulation);

        PhishingSimulation updated = service.updateSimulationStatus(1L, "ACTIVE");
        assertNotNull(updated);
        verify(notificationService, times(1)).sendSimulationReminder(anyString(), anyString(), anyString());
    }
}