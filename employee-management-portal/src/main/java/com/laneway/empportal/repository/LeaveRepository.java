package com.laneway.empportal.repository;

import com.laneway.empportal.entity.Leave;
import com.laneway.empportal.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployeeId(Long employeeId);

    long countByStatus(LeaveStatus status);
}
