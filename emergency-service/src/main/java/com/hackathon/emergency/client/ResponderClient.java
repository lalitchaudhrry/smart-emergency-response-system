package com.hackathon.emergency.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "responder-service")
public interface ResponderClient {

    @PostMapping("/internal/responder/assign")
    Long assignNearestResponder(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    );
}