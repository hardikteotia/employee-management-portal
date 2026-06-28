package com.laneway.empportal.controller;

import com.laneway.empportal.dto.request.LoginRequest;
import com.laneway.empportal.dto.response.AuthenticationResponse;
import com.laneway.empportal.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authenticationService.login(request));
    }
}