package com.laneway.empportal.repository;

import com.laneway.empportal.entity.Employee;
import com.laneway.empportal.enums.EmploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    long countByDeletedFalse();

    long countByDeletedFalseAndEmploymentStatus(EmploymentStatus employmentStatus);

    long countByDeletedFalseAndLocation_NameIgnoreCase(String locationName);
}