package com.laneway.empportal.dto.request;

import com.laneway.empportal.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceRequest {

    @NotNull
    private Long employeeId;

    @NotNull
    private LocalDate date;

    private LocalTime checkIn;

    private LocalTime checkOut;

    @NotNull
    private AttendanceStatus status;
}
