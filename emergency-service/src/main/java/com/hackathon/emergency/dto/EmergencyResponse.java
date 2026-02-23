package com.hackathon.emergency.dto;

import com.hackathon.emergency.entity.EmergencyStatus;

public class EmergencyResponse {

    private Long emergencyId;
    private EmergencyStatus status;
    private Long responderId;

    public EmergencyResponse() {}

    public Long getEmergencyId() {
        return emergencyId;
    }

    public void setEmergencyId(Long emergencyId) {
        this.emergencyId = emergencyId;
    }

    public EmergencyStatus getStatus() {
        return status;
    }

    public void setStatus(EmergencyStatus status) {
        this.status = status;
    }

    public Long getResponderId() {
        return responderId;
    }

    public void setResponderId(Long responderId) {
        this.responderId = responderId;
    }
}