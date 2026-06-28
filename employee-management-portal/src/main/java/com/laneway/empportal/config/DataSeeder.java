package com.laneway.empportal.config;

import com.laneway.empportal.entity.Department;
import com.laneway.empportal.entity.Employee;
import com.laneway.empportal.entity.Location;
import com.laneway.empportal.entity.User;
import com.laneway.empportal.enums.EmploymentStatus;
import com.laneway.empportal.enums.UserRole;
import com.laneway.empportal.repository.DepartmentRepository;
import com.laneway.empportal.repository.EmployeeRepository;
import com.laneway.empportal.repository.LocationRepository;
import com.laneway.empportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final LocationRepository locationRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

        seedAdminUser();

        System.out.println("========== DATA SEEDER FINISHED ==========");
    }

    /**
     * Ensures a known admin login always works so the portal can be signed
     * into out of the box. If the admin user already exists, its password is
     * reset to the known value (handy in dev where the old password may be
     * lost). Otherwise a fresh admin + dedicated employee record is created.
     */
    private void seedAdminUser() {
        String adminUsername = "admin";
        String adminPassword = "Admin@123";

        User existing = userRepository.findByUsername(adminUsername).orElse(null);
        if (existing != null) {
            existing.setPassword(passwordEncoder.encode(adminPassword));
            userRepository.save(existing);
            System.out.println("Data Seeder: Admin user exists. Reset password -> username: admin / password: Admin@123");
            return;
        }

        String adminEmail = "admin.portal@laneway.com";
        Employee adminEmployee = employeeRepository.findByEmail(adminEmail)
                .orElseGet(() -> employeeRepository.save(
                        Employee.builder()
                                .name("Portal Admin")
                                .email(adminEmail)
                                .designation("System Administrator")
                                .dateOfJoining(LocalDate.now())
                                .timezone("UTC")
                                .employmentStatus(EmploymentStatus.ACTIVE)
                                .deleted(false)
                                .build()
                ));

        User admin = User.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .role(UserRole.ADMIN)
                .employee(adminEmployee)
                .build();
        userRepository.save(admin);

        System.out.println("Data Seeder: Created admin login -> username: admin / password: Admin@123");
    }
}