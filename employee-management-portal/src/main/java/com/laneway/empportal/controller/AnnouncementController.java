package com.laneway.empportal.controller;

import com.laneway.empportal.dto.request.AnnouncementRequest;
import com.laneway.empportal.dto.response.AnnouncementResponse;
import com.laneway.empportal.dto.response.ApiResponse;
import com.laneway.empportal.service.AnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<AnnouncementResponse> create(
            @Valid @RequestBody AnnouncementRequest request) {
        return ResponseEntity.ok(announcementService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponse> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(announcementService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementResponse>> getAll() {
        return ResponseEntity.ok(announcementService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AnnouncementRequest request) {
        return ResponseEntity.ok(announcementService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id) {
        announcementService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Announcement deleted successfully.")
                        .build()
        );
    }
}
