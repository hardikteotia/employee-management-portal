package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.request.LeaveRequest;
import com.laneway.empportal.dto.response.LeaveResponse;
import com.laneway.empportal.entity.Employee;
import com.laneway.empportal.entity.Leave;
import com.laneway.empportal.enums.LeaveStatus;
import com.laneway.empportal.exception.ResourceNotFoundException;
import com.laneway.empportal.repository.EmployeeRepository;
import com.laneway.empportal.repository.LeaveRepository;
import com.laneway.empportal.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public LeaveResponse create(LeaveRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        Leave leave = Leave.builder()
                .employee(employee)
                .leaveType(request.getLeaveType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .reason(request.getReason())
                .status(LeaveStatus.PENDING)
                .build();

        leaveRepository.save(leave);

        return mapToResponse(leave);
    }

    @Override
    public LeaveResponse update(Long id, LeaveRequest request) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave not found."));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        leave.setEmployee(employee);
        leave.setLeaveType(request.getLeaveType());
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason());

        leaveRepository.save(leave);

        return mapToResponse(leave);
    }

    @Override
    public LeaveResponse updateStatus(Long id, LeaveStatus status) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave not found."));

        leave.setStatus(status);

        leaveRepository.save(leave);

        return mapToResponse(leave);
    }

    @Override
    public LeaveResponse getById(Long id) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave not found."));

        return mapToResponse(leave);
    }

    @Override
    public List<LeaveResponse> getAll() {

        return leaveRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<LeaveResponse> getByEmployee(Long employeeId) {

        return leaveRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave not found."));

        leaveRepository.delete(leave);
    }


    private LeaveResponse mapToResponse(Leave leave) {

        return LeaveResponse.builder()
                .id(leave.getId())
                .employeeId(leave.getEmployee().getId())
                .employeeName(leave.getEmployee().getName())
                .leaveType(leave.getLeaveType())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .reason(leave.getReason())
                .status(leave.getStatus())
                .build();
    }
}
