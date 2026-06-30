package com.laneway.empportal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeProjectRequest {

    @NotNull
    private Long employeeId;

    @NotNull
    private Long projectId;

    @NotBlank
    private String projectRole;

    @NotNull
    private Integer allocationPercentage;
}
