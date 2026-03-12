# Expense Request Platform

## Overview

A backend system that allows employees to submit expense claims which are reviewed and approved or rejected by managers.  
The platform implements role-based access control and full audit logging to ensure accountability and traceability of all actions.

The system demonstrates backend concepts such as REST API design, transactional business logic, workflow management, and database-driven applications.

---

## Key Features

- Expense submission and approval workflow
- Role-based access control (Employee, Manager, Finance)
- Expense editing (submitter only)
- Full audit logging of all actions
- Database version control using Flyway
- Transactional business logic to ensure workflow consistency

---

## Tech Stack

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA (Hibernate)**
- **Spring Security**
- **PostgreSQL**
- **Flyway**
- **Docker & Docker Compose** (for running the database locally)

---

## Running the Project Locally

This project uses an `application.yml` file for environment-specific configuration.

An example configuration file is provided here:

src/main/resources/application-example.yml

Steps to run the project:

1. Clone the repository
2. Configure your `application.yml`
3. Start the PostgreSQL database using Docker Compose
4. Run the Spring Boot application

---

## System Architecture

The application follows a layered backend architecture.

Client (HTTP)  
↓  
Controller Layer (REST API)  
↓  
Service Layer (Business Logic)  
↓  
Repository Layer (Persistence)  
↓  
PostgreSQL Database  

This separation ensures clear responsibility between API handling, business logic, and data persistence.

---

## Expense Workflow

**Draft**  
The employee is preparing an expense request but has not submitted it yet.

**Submitted**  
The expense request has been submitted and is awaiting review from management.

**Approved**  
The request has been approved by a manager. Finance users can now view and process it.

**Rejected**  
The request has been rejected by management. Finance users can still view the record for reporting purposes.

---

## Role-Based Access Control

**Employee**

- Create expense requests
- Edit their own requests
- Submit expenses for approval

**Manager**

- Review submitted expenses
- Approve or reject expense requests

**Finance**

- View all processed expense requests
- Generate financial reports (future feature)

---

## Example API Endpoints

POST /expenses  
Create a new expense request  

PUT /expenses/{id}/submit  
Submit an expense for approval  

PUT /expenses/{id}/approve  
Approve an expense request  

PUT /expenses/{id}/reject  
Reject an expense request  

GET /expenses  
Retrieve expense requests  

---

## Database Design

Key tables include:

- **users** – application users with assigned roles
- **expenses** – submitted expense claims
- **expense_status** – tracks the workflow state of each expense
- **roles** – defines system permissions
- **audit_logs** – records system actions for traceability

---

## Project Structure

src/main/java/org.example  

config  

domain  
├── entity  
├── enums  
├── repository  
└── service  

web  
├── controller  
├── dto  
└── request  

Main.java  

---

## Security Considerations

- Role-based access control using Spring Security
- Input validation to prevent malformed requests
- Protection against SQL injection via JPA and prepared queries
- Transactional operations to maintain workflow integrity
- Full audit logging of system actions

---

## Design Decisions

- **Flyway** is used to manage version-controlled database migrations.
- **Spring Security** enforces role-based access control across the API.
- **Transactional service methods** ensure that approval workflows remain consistent even if failures occur.

---

## Future Improvements

- Generate expense reports
- Add audit log API endpoints
- Implement integration testing
- Add authentication for user login
- Introduce API documentation (Swagger/OpenAPI)
