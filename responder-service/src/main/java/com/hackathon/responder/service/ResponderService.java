package com.hackathon.responder.service;

import com.hackathon.responder.dto.ResponderResponse;

import java.util.List;

public interface ResponderService {

    List<ResponderResponse> getAvailableResponders();

    ResponderResponse updateStatus(Long responderId, String status);

    void acceptEmergency(Long responderId, Long emergencyId);
}