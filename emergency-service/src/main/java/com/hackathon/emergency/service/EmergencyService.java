package com.hackathon.emergency.service;

import com.hackathon.emergency.client.NotificationClient;
import com.hackathon.emergency.client.ResponderClient;
import com.hackathon.emergency.dto.EmergencyResponse;
import com.hackathon.emergency.dto.NotificationRequest;
import com.hackathon.emergency.dto.SosRequest;
import com.hackathon.emergency.dto.StatusUpdateRequest;
import com.hackathon.emergency.entity.EmergencyRequest;
import com.hackathon.emergency.entity.EmergencyStatus;
import com.hackathon.emergency.repository.EmergencyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmergencyService {

    private final EmergencyRepository repository;
    private final ResponderClient responderClient;
    private final NotificationClient notificationClient;

    public EmergencyService(EmergencyRepository repository,
                            ResponderClient responderClient,
                            NotificationClient notificationClient) {
        this.repository = repository;
        this.responderClient = responderClient;
        this.notificationClient = notificationClient;
    }

    // 🚨 CREATE SOS (userId comes from API Gateway header)
    public EmergencyResponse createSOS(SosRequest request, Long userId) {

        // 1️⃣ Save emergency as CREATED
        EmergencyRequest emergency = new EmergencyRequest();
        emergency.setUserId(userId);
        emergency.setLatitude(request.getLatitude());
        emergency.setLongitude(request.getLongitude());
        emergency.setStatus(EmergencyStatus.CREATED);
        emergency.setCreatedAt(LocalDateTime.now());

        emergency = repository.save(emergency);

        // 2️⃣ Assign nearest responder
        Long responderId = responderClient.assignNearestResponder(
                request.getLatitude(),
                request.getLongitude()
        );

        if (responderId == null) {
            throw new RuntimeException("No responder available");
        }

        // 3️⃣ Update emergency → ASSIGNED
        emergency.setResponderId(responderId);
        emergency.setStatus(EmergencyStatus.ASSIGNED);
        repository.save(emergency);

        // 4️⃣ Notify responder
        NotificationRequest notify = new NotificationRequest();
        notify.setEmergencyId(emergency.getId());
        notify.setUserId(userId);
        notify.setResponderId(responderId);
        notify.setMessage("New emergency assigned. Please respond immediately.");

        notificationClient.sendEmergencyNotification(notify);

        // 5️⃣ Response
        EmergencyResponse response = new EmergencyResponse();
        response.setEmergencyId(emergency.getId());
        response.setStatus(emergency.getStatus());
        response.setResponderId(responderId);

        return response;
    }

    // 🔄 UPDATE STATUS WITH VALIDATION + NOTIFICATION
    public EmergencyResponse updateStatus(Long id, StatusUpdateRequest req) {

        EmergencyRequest emergency = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emergency not found"));

        EmergencyStatus currentStatus = emergency.getStatus();
        EmergencyStatus nextStatus = req.getStatus();

        if (!EmergencyStatusValidator.isValid(currentStatus, nextStatus)) {
            throw new RuntimeException(
                    "Invalid status transition: " + currentStatus + " → " + nextStatus
            );
        }

        emergency.setStatus(nextStatus);
        repository.save(emergency);

        // Notify user & responder
        NotificationRequest notify = new NotificationRequest();
        notify.setEmergencyId(emergency.getId());
        notify.setUserId(emergency.getUserId());
        notify.setResponderId(emergency.getResponderId());
        notify.setMessage("Emergency status updated to " + nextStatus);

        notificationClient.sendEmergencyNotification(notify);

        EmergencyResponse response = new EmergencyResponse();
        response.setEmergencyId(emergency.getId());
        response.setStatus(emergency.getStatus());
        response.setResponderId(emergency.getResponderId());

        return response;
    }

    // 🔍 GET EMERGENCY BY ID
    public EmergencyResponse getEmergencyById(Long id) {

        EmergencyRequest emergency = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emergency not found"));

        EmergencyResponse response = new EmergencyResponse();
        response.setEmergencyId(emergency.getId());
        response.setStatus(emergency.getStatus());
        response.setResponderId(emergency.getResponderId());

        return response;
    }

    // 📜 GET EMERGENCY HISTORY BY USER
    public List<EmergencyResponse> getEmergenciesByUser(Long userId) {

        List<EmergencyRequest> emergencies =
                repository.findByUserId(userId);

        return emergencies.stream().map(emergency -> {
            EmergencyResponse response = new EmergencyResponse();
            response.setEmergencyId(emergency.getId());
            response.setStatus(emergency.getStatus());
            response.setResponderId(emergency.getResponderId());
            return response;
        }).collect(Collectors.toList());
    }
}