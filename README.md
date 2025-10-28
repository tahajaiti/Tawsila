# Tawsila - Optimized Delivery Tour Management System

## Project Overview

Tawsila is a Spring Boot-based delivery route optimization system designed to help logistics companies reduce fuel costs and improve efficiency. The application manages a heterogeneous vehicle fleet and automatically optimizes delivery routes using advanced algorithms.

The system compares two optimization algorithms:
- **Nearest Neighbor**: Fast but generates longer routes (~180km average for 100 deliveries)
- **Clarke & Wright**: Slower but significantly reduces distances (up to 28% improvement)

## Key Features

- Complete CRUD operations for deliveries, tours, vehicles, and warehouses
- Automated route optimization with algorithm comparison
- Vehicle capacity and constraint management (weight, volume, delivery count)
- Delivery status tracking (PENDING, IN_TRANSIT, DELIVERED, FAILED)
- GPS coordinate-based distance calculations
- RESTful API with Swagger documentation

## Technology Stack

### Core Technologies
- **Java 17+**
- **Spring Boot** - Main framework
- **Spring Data JPA** - Data persistence
- **H2 Database** - In-memory database
- **Maven** - Dependency management

### Spring Configuration
- XML-based dependency injection (`applicationContext.xml`)
- Property-based configuration (`application.properties`)
- Manual bean configuration (no `@Autowired`, `@Component`, `@Service` annotations)

### Dependencies
- **Liquibase** - Database migration management
- **Lombok** - Boilerplate code reduction
- **Spring Boot DevTools** - Development tools
- **Swagger/OpenAPI** - API documentation
- **JUnit** - Unit testing
- **SLF4J** - Logging framework

### Development Tools
- **Git** - Version control (branch: `test/nearest`)
- **JIRA** - Project management (SCRUM)
- **SonarLint** - Code quality analysis
- **Postman** - API testing

## Project Structure

```
src/
├── main/
│   ├── java/com/kyojin/tawsila/
│   │   ├── TawsilaApplication.java          # Main application class
│   │   ├── controller/                       # REST controllers
│   │   │   ├── DeliveryController.java
│   │   │   ├── TourController.java
│   │   │   └── VehicleController.java
│   │   ├── dto/                             # Data Transfer Objects
│   │   │   ├── DeliveryDTO.java
│   │   │   ├── DeliveryStatusDTO.java
│   │   │   ├── TourDTO.java
│   │   │   └── VehicleDTO.java
│   │   ├── entity/                          # JPA entities
│   │   │   ├── Delivery.java
│   │   │   ├── Tour.java
│   │   │   ├── Vehicle.java
│   │   │   └── Warehouse.java
│   │   ├── enums/                           # Enumerations
│   │   │   ├── AlgorithmType.java
│   │   │   ├── DeliveryStatus.java
│   │   │   └── VehicleType.java
│   │   ├── exception/                       # Exception handling
│   │   │   ├── AppExceptionHandler.java
│   │   │   ├── BadRequestException.java
│   │   │   ├── CapacityExceededException.java
│   │   │   └── ErrorResponse.java
│   │   ├── mapper/                          # Entity-DTO mappers
│   │   ├── optimizer/                       # Route optimization algorithms
│   │   │   ├── TourOptimizer.java          (interface)
│   │   │   ├── NearestNeighborOptimizer.java
│   │   │   └── ClarkeWrightOptimizer.java
│   │   ├── repository/                      # Data repositories
│   │   ├── service/                         # Business logic
│   │   │   └── TourService.java
│   │   └── util/                           # Utility classes
│   └── resources/
│       ├── application.properties           # Application configuration
│       ├── applicationContext.xml           # Spring bean definitions
│       └── db/changelog/                    # Liquibase migrations
│           ├── db.changelog-master.yaml
│           └── changelogs/
│               ├── delivery-changelog.yaml
│               ├── tour-changelog.yaml
│               ├── vehicle-changelog.yaml
│               └── warehouse-changelog.yaml
└── test/
    ├── java/com/kyojin/tawsila/            # Unit tests
    └── resources/
        └── applicationContext-test.xml      # Test configuration
```

## Business Domain

### Entities

**Vehicle**: Represents delivery vehicles with different types and capacities
- Types: BIKE (50kg, 0.5m³, 15 deliveries max), VAN (1000kg, 8m³, 50 deliveries max), TRUCK (5000kg, 40m³, 100 deliveries max)

**Delivery**: Individual delivery with GPS coordinates, weight, volume, and optional time window
- Statuses: PENDING → IN_TRANSIT → DELIVERED/FAILED

**Tour**: Daily route assigned to a vehicle containing ordered deliveries

**Warehouse**: Starting and ending point for all tours (operating hours: 06:00-22:00)

### Optimization Algorithms

**Nearest Neighbor**:
- Greedy approach: always choose the closest unvisited delivery
- Fast computation (~50ms for 100 deliveries)
- Suboptimal routes

**Clarke & Wright**:
- Savings-based approach: merges routes that save the most distance
- Formula: `Savings(i,j) = Distance(Warehouse,i) + Distance(Warehouse,j) - Distance(i,j)`
- Better optimization with acceptable computation time (~200ms)

## Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.x
- Git

### Clone Repository
```bash
git clone git@github.com:tahajaiti/Tawsila.git
cd Tawsila
```

### Run Application
```bash
// with mvn wrapper

./mvnw spring-boot:run
```

The application will start on the port configured in `application.properties` (default: 8080).

## Configuration

### Database (H2)
The H2 console is accessible at: `http://localhost:8080/h2-console`
- JDBC URL: configured in `application.properties`
- Database migrations managed by Liquibase

### Spring Beans
All dependency injection is configured in `applicationContext.xml`. The optimizer strategy pattern allows switching between algorithms without code modification (Open/Closed Principle).

## API Documentation

Access Swagger UI at: `http://localhost:8080/api/v1/swagger.html`

### Main Endpoints
- `GET/POST/PUT/DELETE /api/deliveries` - Delivery management
- `GET/POST/PUT/DELETE /api/tours` - Tour management
- `GET/POST/PUT/DELETE /api/vehicles` - Vehicle management
- `GET /api/tours/{id}/optimized` - Get optimized tour route
- `GET /api/tours/{id}/distance` - Calculate total tour distance

## Testing

### Run Unit Tests
```bash
mvn test
```

### API Testing
Import the Postman collection (to be provided) or use Swagger UI for manual testing.

## Design Patterns

- **Repository Pattern**: Data access abstraction
- **DTO Pattern**: Separation of API and domain models
- **Mapper Pattern**: Entity-DTO conversions
- **Strategy Pattern**: Algorithm selection (TourOptimizer implementations)
- **Dependency Injection**: XML-based Spring configuration

## Development Workflow

### JIRA Integration
Project managed using SCRUM methodology with JIRA board.

## Key Java APIs Used

- **Stream API**: Collection processing and filtering
- **Java Time API**: Date and time handling
- **Collection API & HashMap**: Data structures
- **Optional**: Null-safe operations