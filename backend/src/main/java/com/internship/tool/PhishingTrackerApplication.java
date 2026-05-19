package com.internship.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // Import this!

@SpringBootApplication
@EnableJpaAuditing // Day 10 Update: Tells the framework to auto-populate timestamps
public class PhishingTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhishingTrackerApplication.class, args);
    }
}