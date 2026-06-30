package com.laneway.empportal.service;

import com.laneway.empportal.dto.request.LeaveRequest;
import com.laneway.empportal.dto.response.LeaveResponse;
import com.laneway.empportal.enums.LeaveStatus;

import java.util.List;

public interface LeaveService {

    LeaveResponse create(LeaveRequest request);

    LeaveResponse update(Long id, LeaveRequest request);

    LeaveResponse updateStatus(Long id, LeaveStatus status);

    LeaveResponse getById(Long id);

    List<LeaveResponse> getAll();

    List<LeaveResponse> getByEmployee(Long employeeId);

    void delete(Long id);
}
