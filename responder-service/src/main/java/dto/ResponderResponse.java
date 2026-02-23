package com.hackathon.responder.dto;

import com.hackathon.responder.entity.Responder;

public class ResponderResponse {

    private Long id;
    private String name;
    private String phone;
    private String status;

    public static ResponderResponse fromEntity(Responder r) {
        ResponderResponse dto = new ResponderResponse();
        dto.setId(r.getId());
        dto.setName(r.getName());
        dto.setPhone(r.getPhone());
        dto.setStatus(r.getStatus());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}