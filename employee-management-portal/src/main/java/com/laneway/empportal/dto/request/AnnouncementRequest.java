package com.laneway.empportal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnnouncementRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String createdBy;
}
