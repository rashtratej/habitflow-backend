# HabitFlow Backend

A Spring Boot backend powering a habit-tracking and social-sharing web application where users can track habits, upload aesthetic content, and build consistency through visual progress.

## Project Objective

Build a full-stack application combining:

- Habit tracking system
- User authentication and authorization
- Social content sharing (Pinterest-style inspiration feed)
- Progress analytics and streak tracking

This project is being developed as a portfolio-grade full-stack engineering project.

---

## Features Implemented

### Authentication Module

- User Registration API
- Login API
- Password encryption using BCrypt
- JWT Token Generation
- Spring Security integration

### Database Layer

- PostgreSQL integration
- Hibernate / JPA ORM
- Repository layer implementation

### Backend Architecture

- DTO pattern implementation
- Service layer architecture
- REST API development
- Dependency Injection with Spring Beans

---

## Tech Stack

### Backend

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

### Database

- PostgreSQL

### Tools

- Postman
- IntelliJ IDEA
- Git
- GitHub

---

## API Endpoints Built

### Register User

```http
POST /auth/register
```

### Login User

```http
POST /auth/login
```

---

## Upcoming Features

- JWT Authentication Filter
- Protected Routes
- Habit CRUD APIs
- User Profile System
- Pinterest-style content feed
- File upload support
- Frontend development
- Deployment

---

## Current Development Status

Backend Authentication Layer Completed

Progress: 22%

---

## Project Structure

src/main/java/com/rashtratej/habitTrackerVersion1

config/  
controller/  
dto/  
entity/  
repository/  
service/

---

## Author

Rashtratej Deshmukh