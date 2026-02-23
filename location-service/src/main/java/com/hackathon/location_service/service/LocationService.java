package com.hackathon.location_service.service;

import com.hackathon.location_service.dto.*;
import com.hackathon.location_service.entity.LiveLocation;
import com.hackathon.location_service.repository.LocationRepository;
import com.hackathon.location_service.util.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository repository;

    public void updateLocation(LocationUpdateRequest request) {

        LiveLocation location = repository
                .findByUserId(request.getUserId())
                .orElse(new LiveLocation());

        location.setUserId(request.getUserId());
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        location.setRole(request.getRole());

        repository.save(location);
    }

    public NearbySearchResponse findNearestResponder(
            Double latitude, Double longitude) {

        List<LiveLocation> responders =
                repository.findByRole("RESPONDER");

        return responders.stream()
                .map(responder -> NearbySearchResponse.builder()
                        .responderId(responder.getUserId())
                        .distance(
                                DistanceCalculator.calculateDistance(
                                        latitude,
                                        longitude,
                                        responder.getLatitude(),
                                        responder.getLongitude()
                                ))
                        .build())
                .min(Comparator.comparing(NearbySearchResponse::getDistance))
                .orElse(null);
    }
}