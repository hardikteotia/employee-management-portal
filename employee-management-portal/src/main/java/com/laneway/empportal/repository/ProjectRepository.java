package com.laneway.empportal.repository;

import com.laneway.empportal.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}