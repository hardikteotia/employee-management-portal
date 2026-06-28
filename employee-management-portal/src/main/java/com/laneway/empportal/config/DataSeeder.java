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

        seedUser("admin", "Admin@123", UserRole.ADMIN,
                "Portal Admin", "admin.portal@laneway.com", "System Administrator");
        seedUser("manager", "Manager@123", UserRole.MANAGER,
                "Portal Manager", "manager.portal@laneway.com", "Engineering Manager");
        seedUser("employee", "Employee@123", UserRole.EMPLOYEE,
                "Portal Employee", "employee.portal@laneway.com", "Software Engineer");

        System.out.println("========== DATA SEEDER FINISHED ==========");
    }

    /**
     * Ensures a known login always works so the portal can be signed into out
     * of the box. If the user already exists, its password is reset to the
     * known value (handy in dev where the old password may be lost). Otherwise
     * a fresh user + dedicated employee record is created.
     */
    private void seedUser(String username, String password, UserRole role,
                          String name, String email, String designation) {
        User existing = userRepository.findByUsername(username).orElse(null);
        if (existing != null) {
            existing.setPassword(passwordEncoder.encode(password));
            userRepository.save(existing);
            System.out.println("Data Seeder: User exists. Reset password -> username: " + username
                    + " / password: " + password + " (role: " + role + ")");
            return;
        }

        Employee employee = employeeRepository.findByEmail(email)
                .orElseGet(() -> employeeRepository.save(
                        Employee.builder()
                                .name(name)
                                .email(email)
                                .designation(designation)
                                .dateOfJoining(LocalDate.now())
                                .timezone("UTC")
                                .employmentStatus(EmploymentStatus.ACTIVE)
                                .deleted(false)
                                .build()
                ));

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .employee(employee)
                .build();
        userRepository.save(user);

        System.out.println("Data Seeder: Created login -> username: " + username
                + " / password: " + password + " (role: " + role + ")");
    }
}