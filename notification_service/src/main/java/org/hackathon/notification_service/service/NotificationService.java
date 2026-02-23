package org.hackathon.notification_service.service;

import org.hackathon.notification_service.dto.NotificationRequest;
import org.hackathon.notification_service.model.Notification;
import org.hackathon.notification_service.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final EmailService emailService;

    public NotificationService(NotificationRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    public void sendNotification(NotificationRequest request) {

        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setEmail(request.getEmail());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setTimestamp(LocalDateTime.now());

        repository.save(notification);

        emailService.sendEmail(
                request.getEmail(),
                "Emergency Update - " + request.getType(),
                request.getMessage()
        );
    }
}