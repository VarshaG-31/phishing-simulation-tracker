package com.internship.tool.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    public void sendSimulationReminder(String to, String templateName, String status) {
        try {
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
            System.out.println("-> Notification email successfully sent to: " + to);
        } catch (Exception e) {
            System.err.println("-> Failed to generate or transmit email notification: " + e.getMessage());
        }
    }
}