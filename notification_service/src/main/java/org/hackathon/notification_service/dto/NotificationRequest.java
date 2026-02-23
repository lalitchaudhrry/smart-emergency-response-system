package org.hackathon.notification_service.dto;

public class NotificationRequest {

    private Long userId;
    private String email;
    private String message;
    private String type;

    public NotificationRequest() {}

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
}