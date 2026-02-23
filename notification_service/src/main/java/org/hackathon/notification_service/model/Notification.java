package org.hackathon.notification_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String email;
    private String message;
    private String type;
    private LocalDateTime timestamp;

    public Notification() {}

    public Long getId() {
        return id;
    }
    public Long getUserId() {
        return userId;
    }
    public String getEmail() {
        return email;
    }
    public String getMessage() {
        return message;
    }
    public String getType() {
        return type;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}