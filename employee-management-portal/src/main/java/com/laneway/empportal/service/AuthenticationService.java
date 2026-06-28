package com.laneway.empportal.service;

import com.laneway.empportal.dto.request.LoginRequest;
import com.laneway.empportal.dto.request.RegisterRequest;
import com.laneway.empportal.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(LoginRequest request);
    AuthenticationResponse register(RegisterRequest request);
}