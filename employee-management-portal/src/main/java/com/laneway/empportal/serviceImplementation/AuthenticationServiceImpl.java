package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.request.LoginRequest;
import com.laneway.empportal.dto.request.RegisterRequest;
import com.laneway.empportal.dto.response.AuthenticationResponse;
import com.laneway.empportal.entity.Employee;
import com.laneway.empportal.entity.User;
import com.laneway.empportal.exception.DuplicateResourceException;
import com.laneway.empportal.exception.ResourceNotFoundException;
import com.laneway.empportal.jwt.JwtService;
import com.laneway.empportal.repository.EmployeeRepository;
import com.laneway.empportal.repository.UserRepository;
import com.laneway.empportal.security.CustomUserDetails;
import com.laneway.empportal.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username is already taken.");
        }

        if (userRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new DuplicateResourceException("This employee already has an associated user account.");
        }

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found."));

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .employee(employee)
                .build();

        userRepository.save(user);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}