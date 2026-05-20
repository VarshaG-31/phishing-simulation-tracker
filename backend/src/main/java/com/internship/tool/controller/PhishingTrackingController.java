package com.internship.tool.controller;

import com.internship.tool.entity.PhishingClickLog;
import com.internship.tool.repository.PhishingClickLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/track")
public class PhishingTrackingController {

    private final PhishingClickLogRepository clickLogRepository;

    public PhishingTrackingController(PhishingClickLogRepository clickLogRepository) {
        this.clickLogRepository = clickLogRepository;
    }

    @GetMapping("/click")
    public ResponseEntity<Void> handlePhishingClick(
            @RequestParam Long simId,
            @RequestParam String email,
            HttpServletRequest request) {

        PhishingClickLog log = new PhishingClickLog();
        log.setSimulationId(simId);
        log.setEmployeeEmail(email);
        log.setClickedAt(LocalDateTime.now());
        
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        log.setIpAddress(ipAddress);

        clickLogRepository.save(log);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://www.phishingbox.com/phishing-test-landing-page"));
        
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
