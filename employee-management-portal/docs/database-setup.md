# Database Setup

## Database

MySQL 8

Database Name:

employee_management_portal

## ORM

Spring Data JPA with Hibernate

## Schema Generation Strategy

hibernate.ddl-auto=update

Chosen for rapid development during assessment implementation.

## Tables

* departments
* locations
* projects
* employees
* employee_projects
* users

## Relationships

Employee → Department (ManyToOne)

Employee → Location (ManyToOne)

Employee → Manager (Self Referencing ManyToOne)

EmployeeProject → Employee (ManyToOne)

EmployeeProject → Project (ManyToOne)

User → Employee (OneToOne)

## Design Notes

Employee and User are separated to isolate authentication concerns from business domain data.

EmployeeProject is used as a junction table to support project allocation tracking and staffing roles.
