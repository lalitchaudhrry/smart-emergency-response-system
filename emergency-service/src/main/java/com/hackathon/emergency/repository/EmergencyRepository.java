package com.hackathon.emergency.repository;

import com.hackathon.emergency.entity.EmergencyRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyRepository
        extends JpaRepository<EmergencyRequest, Long> {

    List<EmergencyRequest> findByUserId(Long userId);
}