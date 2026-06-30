package com.laneway.empportal.controller;

import com.laneway.empportal.dto.request.LocationRequest;
import com.laneway.empportal.dto.response.ApiResponse;
import com.laneway.empportal.dto.response.LocationResponse;
import com.laneway.empportal.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationResponse> create(
            @Valid @RequestBody LocationRequest request) {
        return ResponseEntity.ok(locationService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(locationService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAll() {
        return ResponseEntity.ok(locationService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody LocationRequest request) {
        return ResponseEntity.ok(locationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id) {
        locationService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Location deleted successfully.")
                        .build()
        );
    }
}
