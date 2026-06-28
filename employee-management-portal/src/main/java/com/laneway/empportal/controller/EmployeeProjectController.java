package com.laneway.empportal.controller;

import com.laneway.empportal.dto.request.EmployeeProjectRequest;
import com.laneway.empportal.dto.response.ApiResponse;
import com.laneway.empportal.dto.response.EmployeeProjectResponse;
import com.laneway.empportal.service.EmployeeProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allocations")
@RequiredArgsConstructor
public class EmployeeProjectController {

    private final EmployeeProjectService employeeProjectService;

    @PostMapping
    public ResponseEntity<EmployeeProjectResponse> create(
            @Valid @RequestBody EmployeeProjectRequest request) {
        return ResponseEntity.ok(employeeProjectService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProjectResponse> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(employeeProjectService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeProjectResponse>> getAll() {
        return ResponseEntity.ok(employeeProjectService.getAll());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeProjectResponse>> getByEmployee(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeProjectService.getByEmployee(employeeId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<EmployeeProjectResponse>> getByProject(
            @PathVariable Long projectId) {
        return ResponseEntity.ok(employeeProjectService.getByProject(projectId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeProjectResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeProjectRequest request) {
        return ResponseEntity.ok(employeeProjectService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id) {
        employeeProjectService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Allocation deleted successfully.")
                        .build()
        );
    }
}
