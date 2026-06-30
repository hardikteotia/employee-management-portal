package com.laneway.empportal.service;

import com.laneway.empportal.dto.request.ProjectRequest;
import com.laneway.empportal.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse create(ProjectRequest request);

    ProjectResponse update(Long id, ProjectRequest request);

    ProjectResponse getById(Long id);

    List<ProjectResponse> getAll();

    void delete(Long id);
}
