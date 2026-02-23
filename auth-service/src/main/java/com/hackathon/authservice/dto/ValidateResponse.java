package com.hackathon.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidateResponse {
    private String email;
    private String role;
    private boolean valid;
}