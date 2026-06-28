package com.laneway.empportal.controller;

import com.laneway.empportal.dto.request.LeaveRequest;
import com.laneway.empportal.dto.response.ApiResponse;
import com.laneway.empportal.dto.response.LeaveResponse;
import com.laneway.empportal.enums.LeaveStatus;
import com.laneway.empportal.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping
    public ResponseEntity<LeaveResponse> create(
            @Valid @RequestBody LeaveRequest request) {
        return ResponseEntity.ok(leaveService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveResponse> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(leaveService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<LeaveResponse>> getAll() {
        return ResponseEntity.ok(leaveService.getAll());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveResponse>> getByEmployee(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveService.getByEmployee(employeeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody LeaveRequest request) {
        return ResponseEntity.ok(leaveService.update(id, request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<LeaveResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam LeaveStatus status) {
        return ResponseEntity.ok(leaveService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id) {
        leaveService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Leave deleted successfully.")
                        .build()
        );
    }
}
