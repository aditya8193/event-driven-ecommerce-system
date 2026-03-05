# Event Driven E-Commerce System

A microservices based backend system built using Spring Boot and Apache Kafka.

## Tech Stack

Java 17  
Spring Boot 3  
Spring Security  
PostgreSQL  
Redis  
Apache Kafka  
Docker  
JPA / Hibernate

## Architecture

Microservices architecture with event-driven communication using Kafka.

Services:
- User Service (Authentication)
- Product Service (Product Management)
- Order Service (Order Processing)

## Event Flow

Order Service → publishes `order-created`

Product Service → consumes event and reduces stock

Product Service → publishes `stock-reduced`

Order Service → updates order status

## Infrastructure

Docker containers used for:

- PostgreSQL
- Redis
- Kafka
- Zookeeper

## How to Run

1 Start infrastructure

docker-compose up -d

2 Run services

User Service → 8081  
Product Service → 8082  
Order Service → 8083

3 Open Swagger

http://localhost:8081/swagger-ui.html