# mic-corporateorganization

**mic-corporateorganization** is a Java-based microservice designed to manage the lifecycle of company employees, employee accounts, and departments. It ensures efficient data handling and seamless integration within a microservices architecture.

## Getting Started

### Prerequisites

- [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/)
- Java 21+ (for local development)
- Maven (for building the project locally)

### Running with Docker

To run the service using Docker containers:

1. Create a shared Docker network:

   ```bash
   docker network create shared-network
   ```
2. Build and start the containers:

    ```bash
   docker-compose up --build -d --remove-orphans
   ```
3. To stop and remove the containers:

    ```bash
   docker-compose down
   ```
