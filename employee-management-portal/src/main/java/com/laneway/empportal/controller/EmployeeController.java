package com.laneway.empportal.controller;

import com.laneway.empportal.dto.request.EmployeeRequest;
import com.laneway.empportal.dto.response.ApiResponse;
import com.laneway.empportal.dto.response.EmployeeResponse;
import com.laneway.empportal.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(
            @Valid @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(employeeService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(employeeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(employeeService.getAll(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(employeeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id) {

        employeeService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Employee deleted successfully.")
                        .build()
        );
    }
}