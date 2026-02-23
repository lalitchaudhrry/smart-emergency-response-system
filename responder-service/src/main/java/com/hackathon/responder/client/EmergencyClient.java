package com.hackathon.responder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "emergency-service", url = "http://localhost:8082")  // <-- Emergency Service ka port
public interface EmergencyClient {

    @PutMapping("/emergency/{id}/status")
    void updateEmergencyStatus(@PathVariable("id") Long emergencyId,
                               @RequestParam("status") String status);
}