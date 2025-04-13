# Distributed E-commerce Order Processing System (Demo)

This project demonstrates a simplified microservices-based backend for processing e-commerce orders asynchronously using Java, Spring Boot, Apache Kafka, and Docker.

## Architecture

*   **Order Service (Port 8081):** Receives REST API requests to create orders, persists them to an H2 database, and publishes an `OrderCreatedEvent` to Kafka.
*   **Inventory Service (Port 8082):** Listens for `OrderCreatedEvent` messages from Kafka, checks/updates product inventory in its H2 database, and logs the outcome.
*   **Notification Service (Port 8083):** Listens for `OrderCreatedEvent` messages and simulates sending a notification by logging to the console.
*   **Apache Kafka (Port 9092):** Acts as the message broker for asynchronous communication between services.
*   **Zookeeper (Port 2181):** Required by Kafka for coordination.

## Technology Stack

*   Java 17+
*   Spring Boot 3.x
*   Spring Kafka
*   Spring Data JPA
*   Apache Kafka
*   H2 Database (In-Memory)
*   Maven
*   Docker & Docker Compose
*   Lombok

## Prerequisites

*   JDK 17 or later
*   Apache Maven
*   Docker & Docker Compose

## How to Build

Navigate to the project's root directory (`distributed-ecommerce/`) and run the Maven build command:

```bash
mvn clean package -DskipTests