package com.laneway.empportal.service;

import com.laneway.empportal.dto.request.DepartmentRequest;
import com.laneway.empportal.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse create(DepartmentRequest request);

    DepartmentResponse update(Long id, DepartmentRequest request);

    DepartmentResponse getById(Long id);

    List<DepartmentResponse> getAll();

    void delete(Long id);
}
