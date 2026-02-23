package com.hackathon.location_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Double latitude;

    private Double longitude;

    private String role; // USER or RESPONDER
}