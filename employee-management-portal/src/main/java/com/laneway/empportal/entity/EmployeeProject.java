package com.laneway.empportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private String projectRole;

    @Column(nullable = false)
    private Integer allocationPercentage;
}