# Employee Module

## Purpose

This module manages all employee-related operations within the Employee Management Portal.

## Features

- Create Employee
- Update Employee
- View Employee
- List Employees
- Soft Delete Employee

## Relationships

Employee
├── Department
├── Location
├── Manager (Self Reference)

## APIs

POST /api/employees

GET /api/employees

GET /api/employees/{id}

PUT /api/employees/{id}

DELETE /api/employees/{id}



## Employee CRUD

### Endpoints

| Method | Endpoint              | Description                |
| ------ | --------------------- | -------------------------- |
| POST   | `/api/employees`      | Create Employee            |
| GET    | `/api/employees`      | List Employees (Paginated) |
| GET    | `/api/employees/{id}` | Get Employee By Id         |
| PUT    | `/api/employees/{id}` | Update Employee            |
| DELETE | `/api/employees/{id}` | Soft Delete Employee       |

### Features

* Email uniqueness validation
* Department validation
* Location validation
* Manager validation
* Soft delete
* Pagination


