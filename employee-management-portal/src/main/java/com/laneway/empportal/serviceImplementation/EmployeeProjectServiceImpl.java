package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.request.EmployeeProjectRequest;
import com.laneway.empportal.dto.response.EmployeeProjectResponse;
import com.laneway.empportal.entity.Employee;
import com.laneway.empportal.entity.EmployeeProject;
import com.laneway.empportal.entity.Project;
import com.laneway.empportal.exception.DuplicateResourceException;
import com.laneway.empportal.exception.ResourceNotFoundException;
import com.laneway.empportal.repository.EmployeeProjectRepository;
import com.laneway.empportal.repository.EmployeeRepository;
import com.laneway.empportal.repository.ProjectRepository;
import com.laneway.empportal.service.EmployeeProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeProjectServiceImpl implements EmployeeProjectService {

    private final EmployeeProjectRepository employeeProjectRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    @Override
    public EmployeeProjectResponse create(EmployeeProjectRequest request) {

        if (employeeProjectRepository.existsByEmployeeIdAndProjectId(
                request.getEmployeeId(), request.getProjectId())) {
            throw new DuplicateResourceException(
                    "Employee is already allocated to this project.");
        }

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        EmployeeProject allocation = EmployeeProject.builder()
                .employee(employee)
                .project(project)
                .projectRole(request.getProjectRole())
                .allocationPercentage(request.getAllocationPercentage())
                .build();

        employeeProjectRepository.save(allocation);

        return mapToResponse(allocation);
    }

    @Override
    public EmployeeProjectResponse update(Long id, EmployeeProjectRequest request) {

        EmployeeProject allocation = employeeProjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Allocation not found."));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        allocation.setEmployee(employee);
        allocation.setProject(project);
        allocation.setProjectRole(request.getProjectRole());
        allocation.setAllocationPercentage(request.getAllocationPercentage());

        employeeProjectRepository.save(allocation);

        return mapToResponse(allocation);
    }

    @Override
    public EmployeeProjectResponse getById(Long id) {

        EmployeeProject allocation = employeeProjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Allocation not found."));

        return mapToResponse(allocation);
    }

    @Override
    public List<EmployeeProjectResponse> getAll() {

        return employeeProjectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EmployeeProjectResponse> getByEmployee(Long employeeId) {

        return employeeProjectRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EmployeeProjectResponse> getByProject(Long projectId) {

        return employeeProjectRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        EmployeeProject allocation = employeeProjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Allocation not found."));

        employeeProjectRepository.delete(allocation);
    }


    private EmployeeProjectResponse mapToResponse(EmployeeProject allocation) {

        return EmployeeProjectResponse.builder()
                .id(allocation.getId())
                .employeeId(allocation.getEmployee().getId())
                .employeeName(allocation.getEmployee().getName())
                .projectId(allocation.getProject().getId())
                .projectName(allocation.getProject().getName())
                .projectRole(allocation.getProjectRole())
                .allocationPercentage(allocation.getAllocationPercentage())
                .build();
    }
}
