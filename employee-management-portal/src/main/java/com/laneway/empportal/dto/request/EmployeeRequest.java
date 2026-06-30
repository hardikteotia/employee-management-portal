package com.laneway.empportal.dto.request;

import com.laneway.empportal.enums.EmploymentStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String designation;

    @NotNull
    private Long departmentId;

    @NotNull
    private Long locationId;

    private Long managerId;

    @NotNull
    private LocalDate dateOfJoining;

    private String timezone;

    @NotNull
    private EmploymentStatus employmentStatus;
}