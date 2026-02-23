package com.hackathon.emergency.client;

import com.hackathon.emergency.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/internal/notify/emergency")
    void sendEmergencyNotification(
            @RequestBody NotificationRequest request
    );
}