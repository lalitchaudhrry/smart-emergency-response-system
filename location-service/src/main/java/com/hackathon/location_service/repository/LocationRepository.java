package com.hackathon.location_service.repository;

import com.hackathon.location_service.entity.LiveLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<LiveLocation, Long> {

    Optional<LiveLocation> findByUserId(Long userId);

    List<LiveLocation> findByRole(String role);
}