package com.internship.tool.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async; // Day 12 Import
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public NotificationService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    // Day 12 Optimization: Dispatches this execution block immediately to a background thread pool
    @Async
    public void sendSimulationReminder(String to, String templateName, String status) {
        try {
            // Logs the background thread identity so you can witness it in the console
            System.out.println("LOG: [Thread " + Thread.currentThread().getName() + "] Initiating async email processing...");

            // Using Spring's internal message creation to avoid Jakarta dependency conflicts
            var mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariable("templateName", templateName);
            context.setVariable("status", status);

            String htmlContent = templateEngine.process("phishing-reminder", context);

            helper.setTo(to);
            helper.setSubject("⚠️ Daily Tracker Notification: Active Simulation Update");
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            System.out.println("-> Notification email successfully sent to: " + to + " on thread " + Thread.currentThread().getName());
        } catch (Exception e) {
            System.err.println("-> Failed to generate or transmit email notification: " + e.getMessage());
        }
    }
}