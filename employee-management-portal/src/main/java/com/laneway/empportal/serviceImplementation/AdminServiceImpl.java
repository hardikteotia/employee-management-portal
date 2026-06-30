package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.response.AdminDashboardResponse;
import com.laneway.empportal.enums.EmploymentStatus;
import com.laneway.empportal.repository.DepartmentRepository;
import com.laneway.empportal.repository.EmployeeRepository;
import com.laneway.empportal.repository.LocationRepository;
import com.laneway.empportal.repository.ProjectRepository;
import com.laneway.empportal.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    /**
     * A "Remote" location is identified purely by its name. The domain model
     * has no dedicated remote flag, so we derive remote head-count from the
     * location named "Remote" rather than introducing a new column/enum.
     */
    private static final String REMOTE_LOCATION_NAME = "Remote";

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final LocationRepository locationRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboard() {

        return AdminDashboardResponse.builder()
                .totalEmployees(employeeRepository.countByDeletedFalse())
                .activeEmployees(employeeRepository
                        .countByDeletedFalseAndEmploymentStatus(EmploymentStatus.ACTIVE))
                .employeesOnLeave(employeeRepository
                        .countByDeletedFalseAndEmploymentStatus(EmploymentStatus.ON_LEAVE))
                .exitedEmployees(employeeRepository
                        .countByDeletedFalseAndEmploymentStatus(EmploymentStatus.EXITED))
                .totalDepartments(departmentRepository.count())
                .totalLocations(locationRepository.count())
                .totalProjects(projectRepository.count())
                .remoteEmployees(employeeRepository
                        .countByDeletedFalseAndLocation_NameIgnoreCase(REMOTE_LOCATION_NAME))
                .build();
    }
}
