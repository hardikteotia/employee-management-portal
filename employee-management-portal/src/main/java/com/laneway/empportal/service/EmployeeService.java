package com.laneway.empportal.service;

import com.laneway.empportal.dto.request.EmployeeRequest;
import com.laneway.empportal.dto.response.EmployeeResponse;
import com.laneway.empportal.enums.EmploymentStatus;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface EmployeeService {

    EmployeeResponse create(EmployeeRequest request);

    EmployeeResponse update(Long id, EmployeeRequest request);

    EmployeeResponse getById(Long id);

    Page<EmployeeResponse> getAll(int page, int size);

    Page<EmployeeResponse> searchAndFilter(String keyword, Long departmentId,
                                           Long locationId, EmploymentStatus status,
                                           int page, int size);

    Map<String, Object> getEmployeeHierarchy(Long id);

    void delete(Long id);
}