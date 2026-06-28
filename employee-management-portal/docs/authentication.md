# Authentication Module

## Objective

Provide secure authentication and authorization using Spring Security and JWT.

## Roles

* ADMIN
* MANAGER
* EMPLOYEE

## Authentication Flow

1. User submits username and password.
2. Spring Security authenticates credentials.
3. JWT token is generated.
4. Client stores the JWT.
5. Every protected request includes:

Authorization: Bearer <token>

6. Backend validates the JWT before allowing access.

## Components

* JwtService
* JwtAuthenticationFilter
* CustomUserDetails
* CustomUserDetailsService
* AuthenticationService
* AuthController
* SecurityConfig

## Future Authorization Rules

### ADMIN

* Full system access

### MANAGER

* Access only employees within their department
* Approve leave
* Assign projects
* View attendance

### EMPLOYEE

* View own profile
* Apply leave
* Mark attendance
* View announcements
* Complete onboarding tasks



## CustomUserDetails

Spring Security requires every authenticated user to implement the `UserDetails` interface.

The `CustomUserDetails` class acts as an adapter between the application's `User` entity and Spring Security.

Responsibilities:

- Provide username
- Provide encrypted password
- Provide user roles as authorities
- Indicate whether the account is active

This separation keeps authentication concerns independent from business entities while allowing Spring Security to perform authentication and authorization.



## CustomUserDetailsService

Spring Security delegates user lookup to the `UserDetailsService` interface.

The application provides a custom implementation that retrieves users from the database using `UserRepository`.

Flow:

1. User submits username.
2. Spring Security calls `loadUserByUsername()`.
3. The repository searches the database.
4. If found, the user is wrapped inside `CustomUserDetails`.
5. If not found, `UsernameNotFoundException` is thrown and authentication fails.

Responsibilities:

- Load user by username.
- Bridge Spring Security with the application's persistence layer.
- Convert `User` entities into `CustomUserDetails`.


## JwtService

The `JwtService` is responsible for all JWT-related operations.

Responsibilities:

- Generate JWT tokens after successful authentication.
- Extract the username from a JWT.
- Validate token authenticity and expiration.

The JWT contains:

- Username (subject)
- Issued timestamp
- Expiration timestamp

Tokens are signed using an HMAC secret key to prevent tampering.


## Password Encoding

Passwords are never stored in plain text.

The application uses BCryptPasswordEncoder provided by Spring Security.

Authentication flow:

1. User registers.
2. Password is hashed using BCrypt.
3. Hash is stored in the database.
4. During login, the submitted password is hashed and compared with the stored hash.

Benefits:

- One-way hashing
- Automatic salting
- Resistant to brute-force attacks


## AuthenticationService

The `AuthenticationService` contains the business logic for authentication.

Responsibilities:

* Authenticate a user's credentials.
* Generate a JWT after successful authentication.
* Return the authentication response to the client.

The controller delegates authentication logic to this service, keeping controllers thin and focused on handling HTTP requests and responses.

Following the Service Interface + Implementation pattern improves maintainability, testability, and allows different authentication strategies to be introduced without changing controller code.


## JwtAuthenticationFilter

The `JwtAuthenticationFilter` runs once for every incoming HTTP request.

Responsibilities:

1. Read the `Authorization` header.
2. Extract the JWT.
3. Validate the JWT.
4. Load the corresponding user.
5. Store the authenticated user inside the `SecurityContext`.

Once the `SecurityContext` is populated, Spring Security considers the request authenticated and authorization rules can be applied.



## SecurityConfig

`SecurityConfig` defines the application's security rules.

Responsibilities:

- Disable CSRF for REST APIs.
- Configure public and protected endpoints.
- Enforce stateless session management.
- Register the JWT authentication filter.
- Expose the `AuthenticationManager` bean.

Only `/api/auth/**` endpoints are publicly accessible. All other endpoints require a valid JWT.



## Repository Updates

To support application bootstrapping and avoid duplicate seed data, repository methods were added for checking the existence of Departments and Locations by name.

### Added Methods

DepartmentRepository

* `findByName(String name)`
* `existsByName(String name)`

LocationRepository

* `findByName(String name)`
* `existsByName(String name)`

These methods are primarily used by the DataSeeder to insert default application data only when it does not already exist.




# Authentication Module

## Objective
Provide secure authentication and authorization using Spring Security and JWT.

## APIs
| Method | Endpoint              | Description                                      |
|--------|-----------------------|--------------------------------------------------|
| POST   | `/api/auth/register`  | Register a new user and link to an employee ID.  |
| POST   | `/api/auth/login`     | Authenticate credentials and receive a JWT.      |

## Roles
* ADMIN
* MANAGER
* EMPLOYEE

## Request & Response

### Request DTOs
* **RegisterRequest**: Requires `username`, `password`, `role`, and `employeeId`.
* **LoginRequest**: Requires `username` and `password`.

### Response DTO
* **AuthenticationResponse**: Returns the `token` (JWT).

## Validation & Business Rules
* Passwords must be encoded using BCrypt before saving.
* Usernames must be unique.
* An `Employee` can only have one `User` account mapped to them (One-to-One).
* If an invalid `employeeId` is provided during registration, throw a `ResourceNotFoundException`.
* If an existing `employeeId` or `username` is reused, throw a `DuplicateResourceException`.

## Security Setup
Only `/api/auth/**` endpoints are publicly accessible. All other application endpoints require a valid Bearer JWT.