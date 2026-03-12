# Expense request platform

## Overview
This is a backend system that allows employees to submit expense claims which are reviewd and apporved or rejected by manager, with full audit logging and role based access control. 

## Key features
expense submission and approval workflow
role based access control (employee, manager, finance)
edit expenses (submitter only)
full audit logging of all actions
database migration via flyway
transactional business logic

## Tech stack
java 21
spring boot 3
spring data JPA (hibernate)
spring security
postgreSQL
flyway
Docker and docker compose (for local database)

## running this project locally

This project uses an `application.yml` file for environment-specific configuration.

An example configuration is provided:

```bash
src/main/resources/application-example.yml

## System architecture
Client (HTTP)
↓
Controller layer (REST API)
↓
Service layer (Business logic)
↓
Repository layer(persistence)
↓
PostgreSQL database

## Expense workflow
-draft
this state is for when the employee is currently writing up the request
-submitted
this state is for after it has been submitted and it was waiting for a decision from managment
-approved
this state is for when the request has been approved by management, this state is also when finance user can view it
-rejected 
this state is for when the request has been rejected by managment, this state is also when finance users can view it

## Role based access control
-Employee
can create and submit expense requests
-manager
can approve/ reject expense requests
-finance
can view all processed expense requests and make reports

## Database design
tables:
-Users
-Expenses
-ExpenseStatus
-roles
-audit logs

## Project structure
src/main/java/org.example
├── config 
├── domain
│ ├── entity
│ ├── enums  
│ ├── repository
│ └── service
├── web
│ ├── controller
│ └── dto
│ └── request
└── Main.java

## Security considerations
- SQL injection prevention
- role based access control
- input validation
- transaction safety
- audit loggin

## Future improvements
- Generate expense reports
- audit log endpoints
- intergration testing
- authentication
