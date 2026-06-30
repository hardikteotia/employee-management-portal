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
public class EmployeeProjectResponse {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private Long projectId;
    private String projectName;
    private String projectRole;
    private Integer allocationPercentage;
}
