package com.hackathon.responder.repository;

import com.hackathon.responder.entity.Responder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponderRepository extends JpaRepository<Responder, Long> {
    List<Responder> findByStatus(String status);
}