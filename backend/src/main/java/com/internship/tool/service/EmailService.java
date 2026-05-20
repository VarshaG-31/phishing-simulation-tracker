package com.internship.tool.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public void sendSimulationEmail(String toEmail, Long simulationId, String templateName) throws MessagingException {
        // Construct tracking URL
        String trackingUrl = "http://localhost:8080/api/track/click?simId=" + simulationId + "&email=" + toEmail;

        String htmlContent = "<h3>Security Notification</h3>"
                + "<p>Please verify your profile credentials immediately:</p>"
                + "<br>"
                + "<a href=\"" + trackingUrl + "\">Verify Account Status</a>";

        System.out.println("\n===== [MOCK EMAIL GENERATED] =====");
        System.out.println("To: " + toEmail);
        System.out.println("Subject: URGENT: Action Required for Account Verification");
        System.out.println("Tracking Link: " + trackingUrl);
        System.out.println("==================================\n");

        if (mailSender != null) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setTo(toEmail);
                helper.setFrom("security-update@internal-system.com");
                helper.setSubject("URGENT: Action Required for Account Verification");
                helper.setText(htmlContent, true);
                mailSender.send(message);
            } catch (Exception e) {
                System.out.println("SMTP Server connection failed. Falling back to local tracking text simulation logs.");
            }
        }
    }
}