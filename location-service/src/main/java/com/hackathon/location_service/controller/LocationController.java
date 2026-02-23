package com.hackathon.location_service.controller;

import com.hackathon.location_service.dto.*;
import com.hackathon.location_service.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService service;

    @PostMapping("/update")
    public String updateLocation(
            @RequestBody LocationUpdateRequest request) {

        service.updateLocation(request);
        return "Location updated successfully";
    }

    @GetMapping("/nearby")
    public NearbySearchResponse findNearby(
            @RequestParam Double latitude,
            @RequestParam Double longitude) {

        return service.findNearestResponder(latitude, longitude);
    }
}