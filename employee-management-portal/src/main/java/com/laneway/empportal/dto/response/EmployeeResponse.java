package com.laneway.empportal.dto.response;

import com.laneway.empportal.enums.EmploymentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EmployeeResponse {

    private Long id;

    private String name;

    private String email;

    private String designation;

    private String department;

    private String location;

    private String manager;

    private LocalDate dateOfJoining;

    private String timezone;

    private EmploymentStatus employmentStatus;
}