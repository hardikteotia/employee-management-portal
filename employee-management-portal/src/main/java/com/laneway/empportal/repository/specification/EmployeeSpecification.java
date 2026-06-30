package com.laneway.empportal.repository.specification;

import com.laneway.empportal.entity.Employee;
import com.laneway.empportal.enums.EmploymentStatus;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    private EmployeeSpecification() {
    }

    public static Specification<Employee> hasDepartment(Long departmentId) {
        return (root, query, cb) ->
                departmentId == null
                        ? cb.conjunction()
                        : cb.equal(root.get("department").get("id"), departmentId);
    }

    public static Specification<Employee> hasLocation(Long locationId) {
        return (root, query, cb) ->
                locationId == null
                        ? cb.conjunction()
                        : cb.equal(root.get("location").get("id"), locationId);
    }

    public static Specification<Employee> hasEmploymentStatus(
            EmploymentStatus status) {
        return (root, query, cb) ->
                status == null
                        ? cb.conjunction()
                        : cb.equal(root.get("employmentStatus"), status);
    }

    public static Specification<Employee> isNotDeleted() {
        return (root, query, cb) ->
                cb.isFalse(root.get("deleted"));
    }

    public static Specification<Employee> searchByNameOrEmail(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), likePattern),
                    cb.like(cb.lower(root.get("email")), likePattern)
            );
        };
    }
}