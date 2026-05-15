package com.hruthvik.jobtracker.controller;

import com.hruthvik.jobtracker.dto.request.AuthRequest;
import com.hruthvik.jobtracker.dto.request.RegisterRequest;
import com.hruthvik.jobtracker.dto.response.AuthResponse;
import com.hruthvik.jobtracker.dto.response.UserResponse;
import com.hruthvik.jobtracker.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe() {
        return ResponseEntity.ok(authService.getMe());
    }
}
