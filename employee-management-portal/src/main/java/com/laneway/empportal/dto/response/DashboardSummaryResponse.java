package com.laneway.empportal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryResponse {

    private long totalEmployees;
    private long activeEmployees;
    private long employeesOnLeave;
    private long exitedEmployees;
    private long totalDepartments;
    private long totalLocations;
    private long totalProjects;
    private long remoteEmployees;
    private long pendingLeaves;
    private long totalAnnouncements;
}
