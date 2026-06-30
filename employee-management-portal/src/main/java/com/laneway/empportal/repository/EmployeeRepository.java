package com.laneway.empportal.repository;

import com.laneway.empportal.entity.Employee;
import com.laneway.empportal.enums.EmploymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long>,
        JpaSpecificationExecutor<Employee> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<Employee> findByDeletedFalse(Pageable pageable);

    long countByDeletedFalse();

    long countByDeletedFalseAndEmploymentStatus(EmploymentStatus employmentStatus);

    long countByDeletedFalseAndLocation_NameIgnoreCase(String locationName);
}
