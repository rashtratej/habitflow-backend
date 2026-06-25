# HabitFlow Backend

A Spring Boot backend powering a secure habit-tracking web application where users can create habits, track daily consistency, and manage personal progress through authenticated APIs.

## Project Objective

Build a portfolio-grade full-stack application combining:

* Secure user authentication and authorization
* Personal habit tracking system
* Progress monitoring and analytics
* Future social-sharing system (Pinterest-style inspiration feed)

This project is being developed as a production-oriented full-stack engineering project.

---

## Features Implemented

### Authentication & Security Module

* User Registration API
* Login API
* Password encryption using BCrypt
* JWT Token Generation
* JWT Authentication Filter
* Spring Security integration
* Protected Routes with Stateless Authentication

### Habit Management Module

* Create Habit API
* Fetch User-Specific Habits API
* Mark Habit as Completed API
* Ownership-Based Authorization Checks
* Prevention of Unauthorized Resource Access
* Habit Analytics API

### Database Layer

* PostgreSQL integration
* Hibernate / JPA ORM
* One-to-Many relationship mapping (User → Habits)
* Repository layer implementation

### Backend Architecture

* DTO pattern implementation
* Service layer architecture
* REST API development
* Dependency Injection with Spring Beans
* Global Exception Handling using @RestControllerAdvice
* Structured API error responses

---

## Tech Stack

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* Maven

### Database

* PostgreSQL

### Tools

* Postman
* IntelliJ IDEA
* Git
* GitHub

---

## API Endpoints Built

### Authentication

```http
POST /auth/register
POST /auth/login
```

### Habit Management

```http
POST /habits/create
GET /habits/my
PATCH /habits/{id}/complete
```

---

## Security Features

* JWT-Based Authentication
* Password Hashing with BCrypt
* Protected API Routes
* User-Specific Data Isolation
* Ownership Verification Before Resource Updates
* Prevention of Unauthorized Habit Modification

---

## Upcoming Features

* Delete Habit API
* Update Habit API
* Habit Analytics Dashboard
* User Profile System
* Profile Picture Upload Support
* Pinterest-style Content Feed
* Media Upload Support
* Frontend Development
* Deployment

---

## Current Development Status

Core Backend Architecture Completed

Progress: 45%

---

## Project Structure

src/main/java/com/rashtratej/habitTrackerVersion1

config/
controller/
dto/
entity/
exception/
repository/
service/

---

## Author

Rashtratej Deshmukh
