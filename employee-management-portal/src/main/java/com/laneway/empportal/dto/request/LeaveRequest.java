package com.laneway.empportal.dto.request;

import com.laneway.empportal.enums.LeaveType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequest {

    @NotNull
    private Long employeeId;

    @NotNull
    private LeaveType leaveType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private String reason;
}
