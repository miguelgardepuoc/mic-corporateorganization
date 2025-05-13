# Antharos Corporate Organization Domain Service

## Overview

The Corporate Organization domain service is a core microservice within the Antharos HR platform ecosystem. It manages the company's employees, departments, job titles and user credentials. This service is built using Domain-Driven Design (DDD) principles and follows CQRS and hexagonal architecture pattern.

## Domain Model

The service is centered around the following core domain concepts:

- **Department**: Organizational units with specific functions (e.g., Technology, Marketing)
- **JobTitle**: Job titles (e.g., Tech Lead, Product Manager)
- **Employee**: Staff members assigned to positions within departments

## Technology Stack

- **Framework**: Spring Boot 3.4
- **Build Tool**: Maven
- **Language**: Java 21
- **Architecture**: Hexagonal Architecture with CQRS and DDD
- **Database**: PostgreSQL
- **Event Bus**: Azure Service Bus

## Domain Events

The service publishes the following domain events:
- `EmployeeHired`
- `EmployeeOnLeave`
- `EmployeeTerminated`
- `EmployeeMarkedAsInactive`

## Getting Started

### Prerequisites

- JDK 21+
- Maven 3.9+
- PostgreSQL 14+
- Docker & Docker Compose

### Installation

```bash
# Clone the repository
git clone https://github.com/miguelgardepuoc/mic-corporateorganization.git
cd mic-corporateorganization

# Build the project
./mvnw clean install -U
```

### Running Locally

```bash
# Start all dependencies with Docker Compose
docker-compose up --build -d --remove-orphans
```

```bash
# Run the service
mvn spring-boot:run
```

The service will be available at `http://localhost:8086/corporate-organization`.
APIs documentation will be available at `http://localhost:8086/corporate-organization/swagger-ui/index.html`.

## Format code

Code is formatted using spotless-maven-plugin applying google java format:
```bash
mvn spotless:apply
```

## Code coverage

Code coverage is measured using JaCoCo. To generate the report:
```bash
mvn clean verify
```
This command will execute all tests and generate JaCoCo reports. An aggregated report is generated under the `aggregate-report` module.

To view the full aggregated coverage report, open the following file in your browser:
```pgsql
aggregate-report/target/site/jacoco-aggregate/index.html
```
This report shows consolidated coverage data across the entire repository.
