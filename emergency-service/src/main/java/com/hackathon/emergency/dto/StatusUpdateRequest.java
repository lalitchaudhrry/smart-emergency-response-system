package com.hackathon.emergency.dto;

import com.hackathon.emergency.entity.EmergencyStatus;

public class StatusUpdateRequest {

    private EmergencyStatus status;

    public StatusUpdateRequest() {}

    public EmergencyStatus getStatus() {
        return status;
    }

    public void setStatus(EmergencyStatus status) {
        this.status = status;
    }
}