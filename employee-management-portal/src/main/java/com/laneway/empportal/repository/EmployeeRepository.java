package com.laneway.empportal.repository;

import com.laneway.empportal.entity.Department;
import com.laneway.empportal.entity.Employee;
import com.laneway.empportal.entity.Location;
import com.laneway.empportal.enums.EmploymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<Employee> findByDeletedFalse(Pageable pageable);

    Page<Employee> findByDepartmentAndDeletedFalse(
            Department department,
            Pageable pageable
    );

    Page<Employee> findByLocationAndDeletedFalse(
            Location location,
            Pageable pageable
    );

    Page<Employee> findByEmploymentStatusAndDeletedFalse(
            EmploymentStatus employmentStatus,
            Pageable pageable
    );
}