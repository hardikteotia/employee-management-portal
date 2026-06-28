package com.laneway.empportal.service;

import com.laneway.empportal.dto.request.AttendanceRequest;
import com.laneway.empportal.dto.response.AttendanceResponse;

import java.util.List;

public interface AttendanceService {

    AttendanceResponse create(AttendanceRequest request);

    AttendanceResponse update(Long id, AttendanceRequest request);

    AttendanceResponse getById(Long id);

    List<AttendanceResponse> getAll();

    List<AttendanceResponse> getByEmployee(Long employeeId);

    void delete(Long id);
}
