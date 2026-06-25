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