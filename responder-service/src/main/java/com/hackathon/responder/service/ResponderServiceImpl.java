package com.hackathon.responder.service;

import com.hackathon.responder.client.EmergencyClient;
import com.hackathon.responder.dto.ResponderResponse;
import com.hackathon.responder.entity.Responder;
import com.hackathon.responder.repository.ResponderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponderServiceImpl implements ResponderService {

    private final ResponderRepository responderRepository;
    private final EmergencyClient emergencyClient;

    public ResponderServiceImpl(ResponderRepository responderRepository,
                                EmergencyClient emergencyClient) {
        this.responderRepository = responderRepository;
        this.emergencyClient = emergencyClient;
    }

    @Override
    public List<ResponderResponse> getAvailableResponders() {
        return responderRepository.findByStatus("AVAILABLE")
                .stream()
                .map(ResponderResponse::fromEntity)
                .toList();
    }

    @Override
    public ResponderResponse updateStatus(Long responderId, String status) {
        Responder responder = responderRepository.findById(responderId)
                .orElseThrow(() -> new RuntimeException("Responder not found"));

        responder.setStatus(status);
        return ResponderResponse.fromEntity(responderRepository.save(responder));
    }

    @Override
    public void acceptEmergency(Long responderId, Long emergencyId) {
        // Mark responder busy
        updateStatus(responderId, "BUSY");

        // Inform emergency-service
        emergencyClient.updateEmergencyStatus(emergencyId, "IN_PROGRESS");
    }
}