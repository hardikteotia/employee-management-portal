package com.laneway.empportal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Aggregated, organisation-wide metrics surfaced on the admin dashboard.
 * All values are simple counts so the payload stays cheap to compute and serialise.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardResponse {

    private long totalEmployees;
    private long activeEmployees;
    private long employeesOnLeave;
    private long exitedEmployees;
    private long totalDepartments;
    private long totalLocations;
    private long totalProjects;
    private long remoteEmployees;
}
