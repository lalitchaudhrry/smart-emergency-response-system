package com.hackathon.authservice.service;

import com.hackathon.authservice.dto.LoginRequest;
import com.hackathon.authservice.dto.RegisterRequest;
import com.hackathon.authservice.dto.ValidateResponse;
import com.hackathon.authservice.entity.Role;
import com.hackathon.authservice.entity.User;
import com.hackathon.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // ✅ PUT TRY-CATCH HERE (instead of direct setRole)
        try {
            user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role");
        }

        userRepository.save(user);
    }
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );
    }
    public ValidateResponse validate(String token) {

        boolean isValid = jwtService.isTokenValid(token);

        if (!isValid) {
            return ValidateResponse.builder()
                    .valid(false)
                    .build();
        }

        return ValidateResponse.builder()
                .email(jwtService.extractEmail(token))
                .role(jwtService.extractRole(token))
                .valid(true)
                .build();
    }
}