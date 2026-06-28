package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.request.ProjectRequest;
import com.laneway.empportal.dto.response.ProjectResponse;
import com.laneway.empportal.entity.Project;
import com.laneway.empportal.exception.ResourceNotFoundException;
import com.laneway.empportal.repository.ProjectRepository;
import com.laneway.empportal.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponse create(ProjectRequest request) {

        Project project = Project.builder()
                .name(request.getName())
                .client(request.getClient())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        projectRepository.save(project);

        return mapToResponse(project);
    }

    @Override
    public ProjectResponse update(Long id, ProjectRequest request) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        project.setName(request.getName());
        project.setClient(request.getClient());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());

        projectRepository.save(project);

        return mapToResponse(project);
    }

    @Override
    public ProjectResponse getById(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        return mapToResponse(project);
    }

    @Override
    public List<ProjectResponse> getAll() {

        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        projectRepository.delete(project);
    }


    private ProjectResponse mapToResponse(Project project) {

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .client(project.getClient())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
    }
}
