package com.hackathon.emergency.controller;

import com.hackathon.emergency.dto.EmergencyResponse;
import com.hackathon.emergency.dto.SosRequest;
import com.hackathon.emergency.dto.StatusUpdateRequest;
import com.hackathon.emergency.service.EmergencyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emergency")
public class EmergencyController {

    private final EmergencyService service;

    public EmergencyController(EmergencyService service) {
        this.service = service;
    }

    // 🚨 CREATE SOS (Only USER, userId from Gateway header)
    @PostMapping("/sos")
    public EmergencyResponse createSOS(
            @RequestBody SosRequest req,
            HttpServletRequest request) {

        // 🔐 STEP 6 — Header validation
        String userHeader = request.getHeader("X-USER-ID");
        String role = request.getHeader("X-ROLE");

        if (userHeader == null) {
            throw new RuntimeException("Missing X-USER-ID header");
        }

        if (role == null) {
            throw new RuntimeException("Missing X-ROLE header");
        }

        if (!"USER".equals(role)) {
            throw new RuntimeException("Only USER can create SOS");
        }

        Long userId = Long.valueOf(userHeader);
        return service.createSOS(req, userId);
    }

    // 🔄 UPDATE STATUS (Only RESPONDER or ADMIN)
    @PutMapping("/{id}/status")
    public EmergencyResponse updateStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest req,
            HttpServletRequest request) {

        // 🔐 STEP 6 — Header validation
        String role = request.getHeader("X-ROLE");

        if (role == null) {
            throw new RuntimeException("Missing X-ROLE header");
        }

        if (!("RESPONDER".equals(role) || "ADMIN".equals(role))) {
            throw new RuntimeException("Access denied");
        }

        return service.updateStatus(id, req);
    }

    // 🔍 GET EMERGENCY BY ID (Any authenticated user)
    @GetMapping("/{id}")
    public EmergencyResponse getEmergency(@PathVariable Long id) {
        return service.getEmergencyById(id);
    }

    // 📜 GET EMERGENCY HISTORY BY USER
    @GetMapping("/user/{userId}")
    public List<EmergencyResponse> getUserEmergencies(
            @PathVariable Long userId) {
        return service.getEmergenciesByUser(userId);
    }
}