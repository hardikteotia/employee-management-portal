package com.laneway.empportal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectRequest {

    @NotBlank
    private String name;

    private String client;

    private LocalDate startDate;

    private LocalDate endDate;
}
