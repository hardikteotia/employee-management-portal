package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.request.LoginRequest;
import com.laneway.empportal.dto.response.AuthenticationResponse;
import com.laneway.empportal.jwt.JwtService;
import com.laneway.empportal.security.CustomUserDetails;
import com.laneway.empportal.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public AuthenticationResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        

        CustomUserDetails userDetails =
                (CustomUserDetails) customUserDetailsService
                        .loadUserByUsername(request.getUsername());




        String token = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}