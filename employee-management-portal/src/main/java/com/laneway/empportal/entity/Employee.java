package com.laneway.empportal.entity;

import com.laneway.empportal.enums.EmploymentStatus;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String designation;

    private LocalDate dateOfJoining;

    private String timezone;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;

    @Builder.Default
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    @JsonManagedReference
    private List<Employee> directReports;
}