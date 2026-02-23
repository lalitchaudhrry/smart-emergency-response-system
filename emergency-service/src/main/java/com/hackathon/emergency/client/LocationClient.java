package com.hackathon.emergency.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.hackathon.emergency.dto.LocationResponse;

@FeignClient(name = "location-service")
public interface LocationClient {

    @GetMapping("/internal/location/current")
    LocationResponse getCurrentLocation(@RequestParam Long userId);
}