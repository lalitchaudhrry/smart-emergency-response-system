package org.hackathon.notification_service.repository;

import org.hackathon.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}