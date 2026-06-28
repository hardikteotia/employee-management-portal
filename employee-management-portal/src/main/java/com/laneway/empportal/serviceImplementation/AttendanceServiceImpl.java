package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.request.AttendanceRequest;
import com.laneway.empportal.dto.response.AttendanceResponse;
import com.laneway.empportal.entity.Attendance;
import com.laneway.empportal.entity.Employee;
import com.laneway.empportal.exception.DuplicateResourceException;
import com.laneway.empportal.exception.ResourceNotFoundException;
import com.laneway.empportal.repository.AttendanceRepository;
import com.laneway.empportal.repository.EmployeeRepository;
import com.laneway.empportal.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public AttendanceResponse create(AttendanceRequest request) {

        if (attendanceRepository.existsByEmployeeIdAndDate(
                request.getEmployeeId(), request.getDate())) {
            throw new DuplicateResourceException(
                    "Attendance for this employee on this date already exists.");
        }

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .date(request.getDate())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .status(request.getStatus())
                .build();

        attendanceRepository.save(attendance);

        return mapToResponse(attendance);
    }

    @Override
    public AttendanceResponse update(Long id, AttendanceRequest request) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attendance not found."));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));

        attendance.setEmployee(employee);
        attendance.setDate(request.getDate());
        attendance.setCheckIn(request.getCheckIn());
        attendance.setCheckOut(request.getCheckOut());
        attendance.setStatus(request.getStatus());

        attendanceRepository.save(attendance);

        return mapToResponse(attendance);
    }

    @Override
    public AttendanceResponse getById(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attendance not found."));

        return mapToResponse(attendance);
    }

    @Override
    public List<AttendanceResponse> getAll() {

        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AttendanceResponse> getByEmployee(Long employeeId) {

        return attendanceRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attendance not found."));

        attendanceRepository.delete(attendance);
    }


    private AttendanceResponse mapToResponse(Attendance attendance) {

        return AttendanceResponse.builder()
                .id(attendance.getId())
                .employeeId(attendance.getEmployee().getId())
                .employeeName(attendance.getEmployee().getName())
                .date(attendance.getDate())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .status(attendance.getStatus())
                .build();
    }
}
