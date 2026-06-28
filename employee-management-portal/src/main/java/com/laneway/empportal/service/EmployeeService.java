package com.laneway.empportal.service;

import com.laneway.empportal.dto.request.EmployeeRequest;
import com.laneway.empportal.dto.response.EmployeeResponse;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    EmployeeResponse create(EmployeeRequest request);

    EmployeeResponse update(Long id, EmployeeRequest request);

    EmployeeResponse getById(Long id);

    Page<EmployeeResponse> getAll(int page, int size);

    void delete(Long id);
}