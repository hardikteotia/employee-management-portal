package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.request.EmployeeRequest;
import com.laneway.empportal.dto.response.EmployeeResponse;
import com.laneway.empportal.entity.Department;
import com.laneway.empportal.entity.Employee;
import com.laneway.empportal.entity.Location;
import com.laneway.empportal.exception.DuplicateResourceException;
import com.laneway.empportal.exception.ResourceNotFoundException;
import com.laneway.empportal.repository.DepartmentRepository;
import com.laneway.empportal.repository.EmployeeRepository;
import com.laneway.empportal.repository.LocationRepository;
import com.laneway.empportal.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final LocationRepository locationRepository;

    @Override
    public EmployeeResponse create(EmployeeRequest request) {

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Employee with this email already exists.");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Location not found."));

        Employee manager = null;

        if (request.getManagerId() != null) {

            manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Manager not found."));
        }

        Employee employee = Employee.builder()
                .name(request.getName())
                .email(request.getEmail())
                .designation(request.getDesignation())
                .dateOfJoining(request.getDateOfJoining())
                .timezone(request.getTimezone())
                .employmentStatus(request.getEmploymentStatus())
                .department(department)
                .location(location)
                .manager(manager)
                .deleted(false)
                .build();

        employeeRepository.save(employee);

        return mapToResponse(employee);
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        if (!employee.getEmail().equals(request.getEmail())
                && employeeRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Employee with this email already exists.");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Location not found."));

        Employee manager = null;

        if (request.getManagerId() != null) {

            manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Manager not found."));
        }

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDesignation(request.getDesignation());
        employee.setDateOfJoining(request.getDateOfJoining());
        employee.setTimezone(request.getTimezone());
        employee.setEmploymentStatus(request.getEmploymentStatus());
        employee.setDepartment(department);
        employee.setLocation(location);
        employee.setManager(manager);

        employeeRepository.save(employee);

        return mapToResponse(employee);
    }

    @Override
    public EmployeeResponse getById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        if (Boolean.TRUE.equals(employee.getDeleted())) {
            throw new ResourceNotFoundException("Employee not found.");
        }

        return mapToResponse(employee);
    }

    @Override
    public Page<EmployeeResponse> getAll(int page, int size) {

        return employeeRepository
                .findByDeletedFalse(PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    @Override
    public void delete(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        employee.setDeleted(true);

        employeeRepository.save(employee);
    }


    private EmployeeResponse mapToResponse(Employee employee) {

        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .designation(employee.getDesignation())
                .department(employee.getDepartment().getName())
                .location(employee.getLocation().getName())
                .manager(
                        employee.getManager() != null
                                ? employee.getManager().getName()
                                : null
                )
                .dateOfJoining(employee.getDateOfJoining())
                .timezone(employee.getTimezone())
                .employmentStatus(employee.getEmploymentStatus())
                .build();
    }
}