# 🧩 Microservices: Inventory Service & Order Service

This repository contains two Spring Boot–based microservices:

- **Inventory Service** – Manages product inventory and stock allocation
- **Order Service** – Creates orders and reserves inventory

Both services are built using modern Java and Spring ecosystem best practices and were created using **Spring Initializr**.

---

## 🛠 Tech Stack

- **Java:** 21
- **Framework:** Spring Boot
- **Build Tool:** Maven
- **Persistence:** Spring Data JPA
- **Database:** H2 (In-memory)
- **Database Migration:** Liquibase
- **Testing:** JUnit 5, Mockito, Spring Boot Test

---

## 🏗 Microservices Overview

### 1️⃣ Inventory Service

**Responsibilities**
- Maintain product inventory
- Retrieve inventory by product ID
- Update and allocate inventory quantities

**APIs**

| Method | Endpoint | Description |
|------|---------|------------|
| GET | `/inventory/{productId}` | Get inventory details for a product |
| POST | `/inventory/update` | Update / allocate inventory |

---

### 2️⃣ Order Service

**Responsibilities**
- Create customer orders
- Reserve inventory via Inventory Service
- Persist order information

**APIs**

| Method | Endpoint | Description |
|------|---------|------------|
| POST | `/order` | Create a new order |

---

## 🗄 Database & Migrations

- Uses **H2 in-memory database**
- Database schema and seed data are managed using **Liquibase**
- Each microservice maintains its **own database and schema**

Liquibase changelogs are located at: **src/main/resources/db/changelog**

## ▶️ Running the Services

### Prerequisites
- Java 21
- Maven 3.9+

### Run Inventory Service
```bash
cd inventory-service
mvn spring-boot:run
```
----
### Run Order Service
```bash
cd order-service
mvn spring-boot:run
```

## 🔍 API Test Scenarios

---

### 1️⃣ Get Inventory by Product ID

**Endpoint**
```http
GET /inventory/{productId}
RESPONSE {
  "productId": 1001,
  "productName": "Laptop",
  "batches": [
    {
      "batchId": 1,
      "quantity": 50,
      "expiryDate": "2025-12-31"
    }
  ]
}
PUT /inventory/update
Enter the request body:
{
  "productId": 1002,
  "quantity": 3
}
RESPONSE:
{
  "message": "Inventory updated successfully for product Smartphone",
  "batchIds": [3, 4]
}
POST /order
REQUEST: {
  "productId": 1002,
  "quantity": 3
}

RESPONSE: {
  "orderId": 5012,
  "productId": 1002,
  "productName": "Smartphone",
  "quantity": 3,
  "status": "PLACED",
  "reservedFromBatchIds": [3],
  "message": "Order placed. Inventory reserved."
}
