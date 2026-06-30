package com.laneway.empportal.service;

import com.laneway.empportal.dto.request.EmployeeProjectRequest;
import com.laneway.empportal.dto.response.EmployeeProjectResponse;

import java.util.List;

public interface EmployeeProjectService {

    EmployeeProjectResponse create(EmployeeProjectRequest request);

    EmployeeProjectResponse update(Long id, EmployeeProjectRequest request);

    EmployeeProjectResponse getById(Long id);

    List<EmployeeProjectResponse> getAll();

    List<EmployeeProjectResponse> getByEmployee(Long employeeId);

    List<EmployeeProjectResponse> getByProject(Long projectId);

    void delete(Long id);
}
