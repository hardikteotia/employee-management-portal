package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.request.DepartmentRequest;
import com.laneway.empportal.dto.response.DepartmentResponse;
import com.laneway.empportal.entity.Department;
import com.laneway.empportal.exception.DuplicateResourceException;
import com.laneway.empportal.exception.ResourceNotFoundException;
import com.laneway.empportal.repository.DepartmentRepository;
import com.laneway.empportal.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponse create(DepartmentRequest request) {

        if (departmentRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Department with this name already exists.");
        }

        Department department = Department.builder()
                .name(request.getName())
                .build();

        departmentRepository.save(department);

        return mapToResponse(department);
    }

    @Override
    public DepartmentResponse update(Long id, DepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        if (!department.getName().equals(request.getName())
                && departmentRepository.existsByName(request.getName())) {

            throw new DuplicateResourceException("Department with this name already exists.");
        }

        department.setName(request.getName());

        departmentRepository.save(department);

        return mapToResponse(department);
    }

    @Override
    public DepartmentResponse getById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        return mapToResponse(department);
    }

    @Override
    public List<DepartmentResponse> getAll() {

        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        departmentRepository.delete(department);
    }


    private DepartmentResponse mapToResponse(Department department) {

        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }
}
