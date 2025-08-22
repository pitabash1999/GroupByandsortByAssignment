# Dataset API Project

## Overview

A RESTful API for storing and querying records with grouping and sorting capabilities, built with Spring Boot and H2 in-memory database.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Git

## Installation & Setup

### 1. Clone the Repository

```bash
git clone GroupByandsortByAssignment
cd GroupByandsortByAssignment
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

**Option 1: Using Maven Spring Boot plugin**
```bash
mvn spring-boot:run
```

**Option 2: Running the JAR file**
```bash
java -jar target/GroupByandsortByAssignment.jar
```

**Option 3: Using IDE**
- Import as Maven project in your IDE (IntelliJ IDEA, Eclipse, etc.)
- Run the main class: `GroupBySortByApplication.java`

## Application Configuration

The application runs on port `8080` by default. You can modify this in `src/main/resources/application.properties`:

```properties
server.port=8080
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## Access Points

- **API Base URL**: http://localhost:8080/api/dataset
- **H2 Database Console**: http://localhost:8080/h2-console
- **JDBC URL**: jdbc:h2:mem:testdb
- **Username**: sa
- **Password**: (leave empty)


### Using Postman

1. Import the Postman collection from `/postman` directory
2. Set base URL to `http://localhost:8080`
3. Use the provided requests to test the API endpoints

## Dependencies

The project uses:
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- JUnit 5
- Mockito
- Jackson for JSON processing

## Troubleshooting

### Common Issues

1. **Port already in use**: Change port in `application.properties`
2. **H2 console not accessible**: Ensure `spring.h2.console.enabled=true`
3. **Tests failing**: Run `mvn clean test` to clear cached test results


## Base URL

```
http://localhost:8080/api/dataset
```

## Database Console

Access the H2 database console at:  
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

## API Endpoints

### 1. Save a Record

Adds a new record to the specified dataset.

**Endpoint:** `POST /api/dataset/{dataset}/record`

**Path Parameters:**
- `dataset` (string): The name of the dataset

**Request Body:**
```json
{
  "name": "Bapuni",
  "age": 27,
  "department": "Finance"
}
```

**Success Response (200 OK):**
```json
{
  "message": "Record added successfully",
  "dataset": "employee_dataset",
  "recordId": 1
}
```

**Error Responses:**
- `400 Bad Request` - Invalid input (null or empty dataset/record)
- `404 Not Found` - Other exceptions

### 2. Query Records

Retrieves records from a dataset with optional grouping or sorting.

**Endpoint:** `GET /api/dataset/{dataset}/query`

**Path Parameters:**
- `dataset` (string): The name of the dataset

**Query Parameters:**
- `groupBy` (optional, string): Field to group records by
- `sortBy` (optional, string): Field to sort records by
- `orderBy` (optional, string, default: "asc"): Sort order - "asc" or "desc"

**Note:** Cannot use both `groupBy` and `sortBy` parameters together.

#### Examples

**Group by Department:**
```
GET /api/dataset/employee_dataset/query?groupBy=department
```

**Response:**
```json
{
  "groupedRecords": {
    "Engineering": [
      {
        "id": 3,
        "name": "Pitabash",
        "age": 27,
        "department": "Engineering"
      }
    ],
    "Finance": [
      {
        "id": 1,
        "name": "Bapuni",
        "age": 27,
        "department": "Finance"
      },
      {
        "id": 2,
        "name": "Muna",
        "age": 27,
        "department": "Finance"
      }
    ]
  }
}
```

**Sort by Name (Ascending):**
```
GET /api/dataset/employee_dataset/query?sortBy=name&orderBy=asc
```

**Response:**
```json
{
  "sortedRecords": [
    {
      "id": 3,
      "name": "Pitabash",
      "age": 27,
      "department": "Engineering"
    },
    {
      "id": 2,
      "name": "Muna",
      "age": 27,
      "department": "Finance"
    },
    {
      "id": 1,
      "name": "Bapuni",
      "age": 27,
      "department": "Finance"
    }
  ]
}
```

**Error Responses:**
- `400 Bad Request` - Both groupBy and sortBy provided, or neither provided
- `500 Internal Server Error` - Other exceptions

## Usage Examples

### Using curl

**Save a record:**
```bash
curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bapuni",
    "age": 27,
    "department": "Finance"
  }'
```

**Query with grouping:**
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department"
```

**Query with sorting:**
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=name&orderBy=desc"
```

## Notes

- The API uses an H2 in-memory database (data persists until application restart)
- Database console is available at `http://localhost:8080/h2-console`
- All endpoints return JSON responses
- Error responses include descriptive messages in the response body
