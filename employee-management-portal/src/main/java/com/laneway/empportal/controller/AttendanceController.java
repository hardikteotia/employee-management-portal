package com.laneway.empportal.controller;

import com.laneway.empportal.dto.request.AttendanceRequest;
import com.laneway.empportal.dto.response.ApiResponse;
import com.laneway.empportal.dto.response.AttendanceResponse;
import com.laneway.empportal.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<AttendanceResponse> create(
            @Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponse> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceResponse>> getAll() {
        return ResponseEntity.ok(attendanceService.getAll());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceResponse>> getByEmployee(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(attendanceService.getByEmployee(employeeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id) {
        attendanceService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Attendance deleted successfully.")
                        .build()
        );
    }
}
