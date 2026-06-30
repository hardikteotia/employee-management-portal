package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.response.DashboardSummaryResponse;
import com.laneway.empportal.enums.EmploymentStatus;
import com.laneway.empportal.enums.LeaveStatus;
import com.laneway.empportal.repository.AnnouncementRepository;
import com.laneway.empportal.repository.DepartmentRepository;
import com.laneway.empportal.repository.EmployeeRepository;
import com.laneway.empportal.repository.LeaveRepository;
import com.laneway.empportal.repository.LocationRepository;
import com.laneway.empportal.repository.ProjectRepository;
import com.laneway.empportal.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    // A "Remote" employee is one whose location is named "Remote". The model
    // has no dedicated remote flag, so we derive it from the location name.
    private static final String REMOTE_LOCATION_NAME = "Remote";

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final LocationRepository locationRepository;
    private final ProjectRepository projectRepository;
    private final LeaveRepository leaveRepository;
    private final AnnouncementRepository announcementRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary() {

        return DashboardSummaryResponse.builder()
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
                .pendingLeaves(leaveRepository.countByStatus(LeaveStatus.PENDING))
                .totalAnnouncements(announcementRepository.count())
                .build();
    }
}
