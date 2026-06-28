package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.response.DashboardSummaryResponse;
import com.laneway.empportal.enums.LeaveStatus;
import com.laneway.empportal.repository.AnnouncementRepository;
import com.laneway.empportal.repository.DepartmentRepository;
import com.laneway.empportal.repository.EmployeeRepository;
import com.laneway.empportal.repository.LeaveRepository;
import com.laneway.empportal.repository.ProjectRepository;
import com.laneway.empportal.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;
    private final LeaveRepository leaveRepository;
    private final AnnouncementRepository announcementRepository;

    @Override
    public DashboardSummaryResponse getSummary() {

        return DashboardSummaryResponse.builder()
                .totalEmployees(employeeRepository.countByDeletedFalse())
                .totalDepartments(departmentRepository.count())
                .totalProjects(projectRepository.count())
                .pendingLeaves(leaveRepository.countByStatus(LeaveStatus.PENDING))
                .totalAnnouncements(announcementRepository.count())
                .build();
    }
}
