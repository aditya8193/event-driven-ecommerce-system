# 🚀 Event-Driven E-Commerce Microservices System

A **production-grade backend system** built using **Microservices Architecture** and **Event-Driven Design**, simulating real-world distributed systems used in modern applications.

---

## 🧠 Project Overview

This project demonstrates:

* Microservices communication using **Apache Kafka**
* **Saga Pattern (Orchestration)** for distributed transactions
* **Transactional Outbox Pattern** for reliable messaging
* **Idempotent Consumers** to prevent duplicate processing
* **Resilience patterns** using Circuit Breaker
* Full **observability stack** (logs, metrics, tracing)

👉 This is NOT a CRUD project — it is a **distributed system**.

---

## 🏗 Architecture

High-Level Flow:

Client > 
API Gateway >
Microservices >
Kafka Event Bus >
Databases

---

## 🧩 Microservices

| Service         | Port | Responsibility                   |
| --------------- | ---- | -------------------------------- |
| API Gateway     | 8080 | Routing, JWT validation          |
| User Service    | 8081 | Authentication & user management |
| Product Service | 8082 | Product & inventory              |
| Order Service   | 8083 | Saga orchestration               |
| Payment Service | 8084 | Payment processing               |

---

## 🔁 Event-Driven Flow

1. Order Service publishes `order-created`
2. Product Service consumes event and updates stock
3. Product Service publishes:

    * `stock-reduced` (success)
    * `stock-failed` (failure)
4. Order Service:

    * Confirms order OR
    * Triggers refund via Payment Service

---

## ⚙️ Reliability Patterns

* ✔ Transactional Outbox Pattern
* ✔ Idempotent Consumer
* ✔ Retry + Dead Letter Queue (DLQ)
* ✔ Circuit Breaker (Resilience4j)

---

## 📊 Observability

* **Tracing:** Zipkin (Micrometer)
* **Logging:** Logstash → Elasticsearch → Kibana
* **Metrics:** Prometheus → Grafana

---

## 🛠 Tech Stack

* Java 17
* Spring Boot 3
* Spring Security (JWT)
* PostgreSQL
* Redis
* Apache Kafka
* Docker
* JPA / Hibernate

---

## 🐳 Infrastructure

Dockerized services:

* PostgreSQL
* Kafka & Zookeeper
* Redis
* Zipkin
* Elasticsearch + Logstash + Kibana
* Prometheus + Grafana

---

## ▶ How to Run

### 1. Start infrastructure

```bash
docker-compose up -d
```

### 2. Run microservices

| Service         | URL                   |
| --------------- | --------------------- |
| User Service    | http://localhost:8081 |
| Product Service | http://localhost:8082 |
| Order Service   | http://localhost:8083 |

---

## 📚 API Documentation

Centralized Swagger UI is available via API Gateway:

http://localhost:8080/swagger-ui.html

All microservices APIs are aggregated into a single interface.

---

## 🧪 Testing Flow

1. Register & Login (User Service)
2. Create Order
3. Observe Kafka events
4. Check order status (CONFIRMED / FAILED)

---

## 📦 Future Improvements

* Kubernetes deployment
* CI/CD pipeline (GitHub Actions)
* Load testing (k6)

---

## ⭐ Key Highlights

✔ Event-driven microservices system
✔ Distributed transactions using Saga
✔ Production-level observability
✔ Fault-tolerant design

---
