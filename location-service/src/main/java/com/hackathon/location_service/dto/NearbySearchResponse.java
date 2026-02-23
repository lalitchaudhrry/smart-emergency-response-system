package com.hackathon.location_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NearbySearchResponse {

    private Long responderId;
    private Double distance;
}