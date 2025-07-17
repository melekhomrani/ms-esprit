# 🛒 Store Microservices Project

A **clean, educational microservices backend** using **Spring Boot 3.5.3**, **Spring Cloud 2025.0.0**, and **Keycloak** for OAuth2, demonstrating:

✅ Spring Boot & Spring Cloud (Eureka, Config, Gateway)  
✅ MongoDB & MySQL persistence  
✅ Feign clients with circuit breaker fallback  
✅ OAuth2 JWT authentication (Keycloak)  
✅ Multi-module Maven structure for professional scalability


---

## 📦 Structure

```
store-microservices/
├── shared/             # Shared DTOs
├── config-server/      # Central configuration server
├── eureka-server/      # Service discovery
├── gateway/            # API Gateway with OAuth2
├── ms-product/         # Product microservice (MongoDB)
├── ms-manufacturer/    # Manufacturer microservice (MySQL)
└── README.md
```

---

## 🛠️ Requirements

- Java 17
- Maven 3.9+
- MongoDB (default port: 27017)
- MySQL (default port: 3306)
- Keycloak (for OAuth2)

---

## 🏗️ Build

From the root (`store-microservices`):

```bash
mvn clean install
```

Builds **all modules in order** using the multi-module Maven structure.

---

## 🚀 Running Services

Run services individually:

```bash
cd config-server && ./mvnw spring-boot:run
cd eureka-server && ./mvnw spring-boot:run
cd gateway && ./mvnw spring-boot:run
cd ms-product && ./mvnw spring-boot:run
cd ms-manufacturer && ./mvnw spring-boot:run
```

Or orchestrate with **Docker Compose** when prepared.

---

## 🌐 Service Ports

- **Config Server:** `http://localhost:8888/`
- **Eureka Server:** `http://localhost:8761/`
- **Gateway:** `http://localhost:8080/`
- **Product Service:** Proxied via Gateway `/api/products`
- **Manufacturer Service:** Proxied via Gateway `/api/manufacturers`

---

## 🔑 Authentication

- Uses **Keycloak** for OAuth2 JWT authentication.
- Gateway validates tokens and forwards them to microservices.
- Microservices trust the Gateway for user context.

---

## ⚡ Hot Reload (Dev)

Run with:

```bash
SPRING_PROFILES_ACTIVE=dev ./mvnw spring-boot:run
```

`spring-boot-devtools` is included for **hot reload support**.

---

## 🛠️ Future Enhancements

✅ Kafka integration for asynchronous communication.  
✅ Prometheus + Grafana monitoring.  
✅ Docker Compose orchestration for local development.  

---

## 👨‍💻 Author

- **Melek Homrani** – for educational and academic demonstration.

---

## 📜 License

For **academic and learning purposes only**.