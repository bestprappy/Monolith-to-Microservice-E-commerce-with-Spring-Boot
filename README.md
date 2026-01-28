# E-Commerce Microservices Platform

A production-ready e-commerce application built with Spring Boot microservices architecture, featuring service discovery, centralized configuration, API gateway, distributed tracing, and messaging.

## Architecture Overview

This project demonstrates a complete microservices ecosystem with the following components:

- **Business Services**: Product, User, and Order management
- **Infrastructure Services**: Eureka (Service Discovery), Config Server, API Gateway
- **Supporting Infrastructure**: PostgreSQL, MongoDB, RabbitMQ
- **Observability**: Distributed tracing with Micrometer and Zipkin, centralized logging

## Technology Stack

- **Framework**: Spring Boot 4.0.1/4.0.2, Spring Cloud 2025.1.0
- **Java Version**: 21
- **Databases**: PostgreSQL (Order, Product), MongoDB (User)
- **Message Broker**: RabbitMQ
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Distributed Tracing**: Micrometer Tracing with Zipkin
- **Build Tool**: Maven (with Maven Wrapper)

## Prerequisites

- **Java Development Kit (JDK) 21** or higher
- **Docker & Docker Compose** (for infrastructure services)
- **Git** (for version control)
- Maven Wrapper included - no global Maven installation required

## Quick Start

### 1. Start Infrastructure Services

```powershell
# Start PostgreSQL, RabbitMQ, and pgAdmin
docker-compose up -d

# Verify containers are running
docker ps
```

**Infrastructure Endpoints:**

- PostgreSQL: `localhost:5432`
- pgAdmin: `http://localhost:5050` (pgadmin4@pgadmin.org / admin)
- RabbitMQ Management UI: `http://localhost:15672` (guest / guest)

### 2. Set Environment Variables

```powershell
# PostgreSQL credentials
$env:DB_USER = "user"
$env:DB_PASSWORD = "password"

# MongoDB connection string
$env:MONGODB_URI = "your-mongodb-connection-string"
```

### 3. Start Services in Order

#### Step 1: Config Server (Port 8888)

```powershell
cd config-server
./mvnw.cmd spring-boot:run
```

#### Step 2: Eureka Server (Port 8761)

```powershell
cd eureka
./mvnw.cmd spring-boot:run
```

Access Eureka Dashboard: `http://localhost:8761`

#### Step 3: Business Services

**Product Service (Port 8081)**

```powershell
cd product
./mvnw.cmd spring-boot:run
```

**User Service (Port 8082)**

```powershell
cd user
./mvnw.cmd spring-boot:run
```

**Order Service (Port 8083)**

```powershell
cd order
./mvnw.cmd spring-boot:run
```

#### Step 4: API Gateway (Port 8080)

```powershell
cd gateway
./mvnw.cmd spring-boot:run
```

## Project Structure

```
ecom-microservices/
├── config-server/              # Centralized Configuration Server
│   ├── src/main/resources/config/
│   │   ├── api-gateway.yml
│   │   ├── order-service.yml
│   │   ├── product-service.yml
│   │   └── user-service.yml
│   └── pom.xml
├── eureka/                     # Service Discovery Server
│   ├── src/
│   └── pom.xml
├── gateway/                    # API Gateway (Spring Cloud Gateway)
│   ├── src/
│   └── pom.xml
├── product/                    # Product Microservice
│   ├── src/main/java/com/ecommerce/product/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   └── exception/
│   └── pom.xml
├── user/                       # User Microservice
│   ├── src/main/java/com/ecommerce/user/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   └── exception/
│   └── pom.xml
├── order/                      # Order Microservice
│   ├── src/main/java/com/ecommerce/order/
│   │   ├── controller/        # OrderController, CartController
│   │   ├── service/           # OrderService, CartService
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   ├── clients/           # Inter-service communication
│   │   └── exception/
│   └── pom.xml
├── docker-compose.yml          # Infrastructure orchestration
├── logs/                       # Application logs
└── Others/                     # Legacy monolith and examples
```

## Service Endpoints

### API Gateway Routes (http://localhost:8080)

| Route                           | Target Service   | Backend Port |
| ------------------------------- | ---------------- | ------------ |
| `/api/product/**`               | Product Service  | 8081         |
| `/api/users/**`                 | User Service     | 8082         |
| `/api/order/**`, `/api/cart/**` | Order Service    | 8083         |
| `/eureka/**`                    | Eureka Dashboard | 8761         |

### Direct Service Access

- **Product Service**: `http://localhost:8081/api/products`
- **User Service**: `http://localhost:8082/api/users`
- **Order Service**: `http://localhost:8083/api/orders`
- **Cart API**: `http://localhost:8083/api/cart`

## API Documentation

### Product Service

```http
GET    /api/products           # Get all products
GET    /api/products/{id}      # Get product by ID
GET    /api/products/search?keyword={keyword}  # Search products
POST   /api/products           # Create product
PUT    /api/products/{id}      # Update product
DELETE /api/products/{id}      # Soft delete product
```

### User Service

```http
GET    /api/users              # Get all users
GET    /api/users/{id}         # Get user by ID
POST   /api/users              # Create user
PUT    /api/users/{id}         # Update user
```

### Order Service

```http
POST   /api/orders             # Create order from cart
                               # Header: X-User-ID
```

### Cart Service

```http
GET    /api/cart               # Get user's cart
                               # Header: X-User-ID
POST   /api/cart               # Add item to cart
                               # Header: X-User-ID
DELETE /api/cart/items/{productId}  # Remove item from cart
                               # Header: X-User-ID
```

## Key Features

### ✅ Implemented

- **Microservices Architecture**: Independent, scalable services with single responsibility
- **Service Discovery**: Automatic service registration and discovery with Eureka
- **Centralized Configuration**: Spring Cloud Config Server with file-based storage
- **API Gateway**: Single entry point with route management
- **Distributed Tracing**: End-to-end request tracing with Micrometer and Zipkin
- **Inter-Service Communication**: REST-based communication with load balancing
- **Message-Driven Architecture**: RabbitMQ for asynchronous communication
- **Database Per Service**: PostgreSQL for Order/Product, MongoDB for User
- **Exception Handling**: Global exception handlers with consistent error responses
- **Health Monitoring**: Spring Boot Actuator endpoints
- **Logging**: Centralized logging with file rotation

### Database Schema

- **Product Service**: PostgreSQL with JPA/Hibernate
- **User Service**: MongoDB with Spring Data MongoDB
- **Order Service**: PostgreSQL with Cart and Order entities

## Configuration Management

All service configurations are centralized in the Config Server:

- `config-server/src/main/resources/config/`
  - `api-gateway.yml` - Gateway routes and filters
  - `order-service.yml` - Order service configuration
  - `product-service.yml` - Product service configuration
  - `user-service.yml` - User service configuration

Services automatically fetch configurations on startup via:

```yaml
spring:
  config:
    import: optional:configserver:http://localhost:8888
```

## Monitoring & Observability

### Actuator Endpoints

All services expose actuator endpoints at `/actuator/*`:

```
/actuator/health        # Health status
/actuator/metrics       # Application metrics
/actuator/info          # Application info
/actuator/env           # Environment properties
```

### Distributed Tracing

- Trace ID propagation across all services
- Sampling rate: 100% (configurable for production)
- Integration with Zipkin for visualization

### Logging

- Rolling file logs in `logs/` directory
- Max file size: 5MB
- Max history: 7 days
- Format: `logs/{service-name}.log`

## Building for Production

```powershell
# Build all services
cd product && ./mvnw.cmd clean package
cd user && ./mvnw.cmd clean package
cd order && ./mvnw.cmd clean package
cd config-server && ./mvnw.cmd clean package
cd eureka && ./mvnw.cmd clean package
cd gateway && ./mvnw.cmd clean package

# Run packaged JARs
java -jar product/target/product-0.0.1-SNAPSHOT.jar
java -jar user/target/user-0.0.1-SNAPSHOT.jar
java -jar order/target/order-0.0.1-SNAPSHOT.jar
```

## Testing

```powershell
# Run tests for a specific service
cd order
./mvnw.cmd test

# Run tests for all services
./mvnw.cmd test -pl product,user,order,gateway,eureka,config-server
```

## Troubleshooting

### Port Conflicts

- Check if ports are available: `netstat -ano | findstr "8080 8081 8082 8083 8761 8888"`
- Kill process: `taskkill /PID <PID> /F`

### Service Not Registering with Eureka

- Ensure Eureka is running on port 8761
- Check `eureka.client.service-url.defaultZone` in service configuration
- Verify network connectivity

### Database Connection Issues

- Verify Docker containers are running: `docker ps`
- Check environment variables are set correctly
- Ensure database credentials match configuration

### Config Server Not Responding

- Verify Config Server is running on port 8888
- Check `spring.config.import` in service application.yml
- Validate config file paths in Config Server

## Roadmap

### Planned Enhancements

- [ ] **Security**: Spring Security with JWT authentication
- [ ] **Input Validation**: Bean Validation on all DTOs
- [ ] **Circuit Breakers**: Resilience4j for fault tolerance
- [ ] **Caching**: Redis for frequently accessed data
- [ ] **API Documentation**: OpenAPI/Swagger integration
- [ ] **Rate Limiting**: API Gateway rate limiting
- [ ] **Database Migrations**: Flyway/Liquibase
- [ ] **Containerization**: Docker images for all services
- [ ] **Kubernetes Deployment**: Helm charts and K8s manifests
- [ ] **CI/CD Pipeline**: GitHub Actions/Jenkins
- [ ] **Comprehensive Testing**: Unit, integration, and contract tests
- [ ] **Saga Pattern**: Distributed transactions
- [ ] **Event Sourcing**: CQRS pattern implementation

## Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## License

This project is available for educational and commercial use. Please add a LICENSE file (MIT, Apache-2.0, etc.) if you plan to distribute or share this project.

## Support

For issues, questions, or contributions, please open an issue on the GitHub repository.

---

**Built with ❤️ using Spring Boot and Spring Cloud**
