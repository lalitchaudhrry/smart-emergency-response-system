package com.hackathon.responder.controller;

import com.hackathon.responder.dto.ResponderResponse;
import com.hackathon.responder.dto.ResponderStatusRequest;
import com.hackathon.responder.service.ResponderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responders")
public class ResponderController {

    private final ResponderService responderService;

    public ResponderController(ResponderService responderService) {
        this.responderService = responderService;
    }

    @GetMapping("/available")
    public List<ResponderResponse> getAvailableResponders() {
        return responderService.getAvailableResponders();
    }

    @PutMapping("/{id}/status")
    public ResponderResponse updateStatus(@PathVariable Long id,
                                          @RequestBody ResponderStatusRequest request) {
        return responderService.updateStatus(id, request.getStatus());
    }

    @PostMapping("/{id}/accept/{emergencyId}")
    public void acceptEmergency(@PathVariable Long id,
                                @PathVariable Long emergencyId) {
        responderService.acceptEmergency(id, emergencyId);
    }
}