# Admin Dashboard

Organisation-wide summary metrics for users with the `ADMIN` role.

## Endpoint

```
GET /api/admin/dashboard
```

### Authentication & authorization

- Requires a valid JWT in the `Authorization: Bearer <token>` header (the portal is stateless — see [authentication.md](authentication.md)).
- Restricted to the `ADMIN` role via `@PreAuthorize("hasRole('ADMIN')")` on `AdminController` (method security is enabled by `@EnableMethodSecurity` in `SecurityConfig`).
- A `MANAGER` or `EMPLOYEE` token is authenticated but not authorized, so it receives **403 Forbidden**.
- A missing or invalid token receives **401/403** before reaching the controller.

### Request

No path variables, query parameters, or request body.

| Header          | Value              | Required |
|-----------------|--------------------|----------|
| `Authorization` | `Bearer <jwt>`     | Yes      |

### Response `200 OK`

```json
{
  "totalEmployees": 12,
  "activeEmployees": 9,
  "employeesOnLeave": 2,
  "exitedEmployees": 1,
  "totalDepartments": 4,
  "totalLocations": 3,
  "totalProjects": 6,
  "remoteEmployees": 5
}
```

| Field              | Type   | Description                                                                 |
|--------------------|--------|-----------------------------------------------------------------------------|
| `totalEmployees`   | `long` | Non-deleted employees (all employment statuses).                            |
| `activeEmployees`  | `long` | Non-deleted employees with `employmentStatus = ACTIVE`.                     |
| `employeesOnLeave` | `long` | Non-deleted employees with `employmentStatus = ON_LEAVE`.                   |
| `exitedEmployees`  | `long` | Non-deleted employees with `employmentStatus = EXITED`.                     |
| `totalDepartments` | `long` | Total department records.                                                   |
| `totalLocations`   | `long` | Total location records.                                                     |
| `totalProjects`    | `long` | Total project records.                                                      |
| `remoteEmployees`  | `long` | Non-deleted employees whose location name is `"Remote"` (case-insensitive). |

### Error responses

| Status | When                                                        |
|--------|-------------------------------------------------------------|
| `401`  | No token, or an expired/invalid token.                      |
| `403`  | Authenticated but the user is not an `ADMIN`.               |

## Implementation notes

- **Counts only.** Every value is a repository `count` query, so the payload is cheap to compute and serialise. No employee lists are loaded.
- **Soft deletes respected.** All employee metrics filter on `deleted = false` so soft-deleted records never inflate the totals.
- **Employees on leave** is derived from `EmploymentStatus.ON_LEAVE` on the employee record, not from a separate leave-request entity.
- **Remote employees** are derived from the location named `"Remote"`. The domain model has no dedicated `isRemote` flag or `REMOTE` enum, so to avoid changing the existing architecture this metric counts non-deleted employees whose `location.name` equals `"Remote"` (case-insensitive). Seed a `Location` named `Remote` and assign employees to it for this count to be non-zero.

## Architecture

Follows the existing layering and package structure:

| Layer      | Type                                                          |
|------------|---------------------------------------------------------------|
| Controller | `controller.AdminController`                                  |
| Service    | `service.AdminService` → `serviceImplementation.AdminServiceImpl` |
| DTO        | `dto.response.AdminDashboardResponse`                         |
| Data       | `EmployeeRepository`, `DepartmentRepository`, `LocationRepository`, `ProjectRepository` (Spring Data derived `count` queries) |

The new derived query methods on `EmployeeRepository`:

```java
long countByDeletedFalse();
long countByDeletedFalseAndEmploymentStatus(EmploymentStatus employmentStatus);
long countByDeletedFalseAndLocation_NameIgnoreCase(String locationName);
```

## Try it

A ready-to-import Postman collection lives at
[`admin-dashboard.postman_collection.json`](admin-dashboard.postman_collection.json). It logs in as the seeded `admin` user, stores the JWT in a collection variable, and calls the dashboard endpoint.

```bash
# 1. Log in and capture the token
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin@123"}' | jq -r .token)

# 2. Call the dashboard
curl -s http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Bearer $TOKEN"
```
