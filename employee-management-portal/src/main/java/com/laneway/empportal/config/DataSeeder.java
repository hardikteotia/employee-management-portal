package com.laneway.empportal.config;

import com.laneway.empportal.entity.Department;
import com.laneway.empportal.entity.Employee;
import com.laneway.empportal.entity.Location;
import com.laneway.empportal.enums.EmploymentStatus;
import com.laneway.empportal.repository.DepartmentRepository;
import com.laneway.empportal.repository.EmployeeRepository;
import com.laneway.empportal.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final LocationRepository locationRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("========== DATA SEEDER STARTED ==========");

        if (departmentRepository.count() == 0) {
            Department dept = Department.builder().name("Engineering").build();
            departmentRepository.save(dept);

            Location loc = Location.builder().name("Headquarters").build();
            locationRepository.save(loc);

            Employee emp = Employee.builder()
                    .name("System Admin")
                    .email("admin@laneway.com")
                    .designation("Administrator")
                    .department(dept)
                    .location(loc)
                    .dateOfJoining(LocalDate.now())
                    .timezone("UTC")
                    .employmentStatus(EmploymentStatus.ACTIVE)
                    .deleted(false)
                    .build();
            employeeRepository.save(emp);

            System.out.println("Data Seeder: Created default Department, Location, and Employee (ID: 1)");
        } else {
            System.out.println("Data Seeder: Database already contains data. Skipping default insertion.");
        }

        System.out.println("========== DATA SEEDER FINISHED ==========");
    }
}