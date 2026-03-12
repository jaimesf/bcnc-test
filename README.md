# BCNC Code Test

This is a REST service that provides a price query endpoint. It allows you to find the applicable price for a product of a specific brand on a given date.

## Features

- REST API to find the applicable price for a product.
- Hexagonal architecture for a clean and maintainable design.
- API documentation with Swagger UI.
- Comprehensive unit and integration tests.
- Code formatting with Spotless.

## Prerequisites

- Java 25
- Maven 3.9.x

## Build and Run

To build and run the application, use the following commands:

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/jaimesf/bcnc-test.git
    cd bcnc-test
    ```

2.  **Build the project:**
    ```bash
    mvn clean install
    ```

3.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

The application will be available at `http://localhost:8080/bcnc-test`.

## API Documentation

The API documentation is available through Swagger UI. Once the application is running, you can access it at:

[http://localhost:8080/bcnc-test/swagger-ui.html](http://localhost:8080/bcnc-test/swagger-ui.html)

### GET /prices

Retrieves the applicable price for a given brand, product, and date.

**Parameters:**

| Name               | Type          | Description                                      | Example                |
| ------------------ | ------------- | ------------------------------------------------ | ---------------------- |
| `brand-id`         | `Long`        | The identifier of the brand.                     | `1`                    |
| `product-id`       | `Integer`     | The identifier of the product.                   | `35455`                |
| `application-date` | `LocalDateTime` | The date for which to find the applicable price. | `2020-06-14T10:00:00` |

**Example Success Response (200 OK):**

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.5
}
```

**Example Error Response (404 Not Found):**

```json
{
  "message": "No price found for brand 2, product 99999, and date 2020-06-16T21:00:00",
  "timestamp": "2023-10-27T10:30:00.123456"
}
```

## Architectural Decisions

This project is built upon a **Hexagonal Architecture (Ports and Adapters)**. The main goal is to isolate the core business logic from external concerns like frameworks, databases, and UI. This makes the application easier to test, maintain, and evolve.

### Project Structure

The project is divided into three main layers, each with a specific responsibility:

-   `domain`: This is the core of the application and is completely independent of any framework. It contains:
    -   `model`: The business entities (e.g., `PriceDomain`).
    -   `repository`: The output port interfaces (e.g., `PriceRepository`) that define the contract for data persistence.
    -   `service`: The input port interfaces (e.g., `PriceService`) that define the available business operations.
    -   `exception`: Custom business exceptions (e.g., `PriceNotFoundException`).

-   `application`: This layer acts as an orchestrator. It contains the implementations of the input ports defined in the domain.
    -   `service`: The implementation of the business logic (e.g., `PriceServiceImpl`).

-   `infrastructure`: This layer contains the concrete implementations of the adapters that interact with the outside world. It is explicitly divided into `input` and `output` to better reflect the flow of control:
    -   `input` (Driving Adapters): Handles incoming requests that drive the application. It contains the REST controller, response DTOs, and API-specific mappers.
    -   `output` (Driven Adapters): Handles the implementation of output ports, connecting to external systems like databases. It contains the JPA entities, the implementation of the repository port, and persistence-related mappers.

### Design Decisions

-   **Price Disambiguation Strategy**: The core business requirement is to select the correct price when multiple tariffs overlap. This is solved by delegating the logic to the database through a specific query method in Spring Data JPA: `findFirst...OrderByPriorityDesc`. This approach is highly efficient as it filters and sorts the results directly in the database, returning only the single, highest-priority record. This avoids fetching multiple records into memory and processing them in the application layer.
-   **Database Schema Interpretation**: Although the exercise did not explicitly require the creation of a `BRAND` table, the description of the `PRICES` table mentioned that `BRAND_ID` is a foreign key. Following database design best practices, a `BRAND` table was created to enforce referential integrity and normalize the data model. This makes the schema more robust and scalable.
-   **REST API Design:** The API is designed to be RESTful. The endpoint `GET /prices` is used to query the prices, which is a natural fit for a read operation. The parameters are passed as query parameters, which is a standard practice for filtering resources. The parameter names follow the `kebab-case` convention.
-   **Centralized Exception Handling:** A global exception handler (`@RestControllerAdvice`) is used to catch exceptions from different layers of the application. This approach abstracts the error handling logic from the business and controller layers, keeping them clean and focused on their primary responsibilities. It also ensures that all API error responses are consistent and follow a standard JSON format.
-   **Database Indexing Strategy**: To ensure high performance for the main query, a **composite index** has been created on the `PRICES` table. The index `(BRAND_ID, PRODUCT_ID, START_DATE, END_DATE, PRIORITY)` is specifically designed to match the query's filter and sort criteria. This allows the database to perform a highly efficient index seek, avoiding full table scans and in-memory sorting, which is crucial for scalability.
-   **Querying Strategy**: For data retrieval, the project uses Spring Data JPA's **query methods** (also known as "named methods"). This approach is clean and readable for simple queries like the one required in this exercise. For more complex scenarios involving multiple joins or custom logic, the design allows for an easy transition to more powerful solutions like **Specification** or native queries with `@Query`, without altering the core architecture.
-   **Build Tool**: The project uses **Maven** as its build and dependency management tool. Maven is the most widely adopted tool in the Java ecosystem, especially in corporate environments. Its declarative approach with the `pom.xml`, its robust dependency management, and its extensive plugin ecosystem make it a reliable and predictable choice for building applications.
-   **Choice of Java Version**: The project is developed using **Java 25**, the latest version available at the time of development. This choice allows the project to benefit from the most recent language features (like Records, used extensively in the DTOs and domain model), performance improvements in the JVM, and the latest security updates.
-   **Spring Boot Version**: **Spring Boot 3.x** was chosen over the newer version 4. This is a strategic decision to prioritize **stability and broad compatibility**. Version 3 is a mature and battle-tested release with long-term support, ensuring a stable foundation for the application. In contrast, a brand-new major version might introduce breaking changes, have a less mature ecosystem of compatible libraries, and potentially exhibit initial instabilities.
-   **Use of Records**: The project leverages Java Records for defining immutable data carriers in the **domain model** (`PriceDomain`, `BrandDomain`) and the **API layer** (`PriceResponse`, `ErrorResponse`). This reduces boilerplate code and enforces immutability. However, records are **not used for JPA entities**. This is because JPA requires a no-argument constructor to create proxy objects for lazy loading and dirty checking, a requirement that conflicts with the design of records, which have a canonical constructor with all fields.
-   **Externalized Configuration**: The project does not include environment-specific property files (e.g., `application-pro.yml`). This is a deliberate design choice. In a real-world scenario, sensitive and environment-specific configurations (like database credentials) should be provided from an external source at runtime (e.g., environment variables, a configuration server like Spring Cloud Config, or a secret manager).
-   **In-Memory Database (H2):** For simplicity and to meet the requirements of the exercise, an H2 in-memory database is used. The database is initialized with the provided data on startup.
-   **MapStruct:** This library is used for mapping between domain entities and DTOs. It generates the mapping code at compile time, which is fast and type-safe.
-   **SOLID Principles:** The code is written following the SOLID principles to ensure it is maintainable and scalable.

## Testing Strategy

The project has a comprehensive testing strategy covering different levels:

-   **Unit Tests**: These tests focus on individual components in isolation (e.g., services, mappers, exception handlers). They use Mockito to mock dependencies, ensuring that each class works as expected.
-   **Integration Tests**: These tests validate the behavior of the application from the outside in, from the REST endpoint to the database. They use Spring Boot's testing support (`@SpringBootTest` and `MockMvc`) to simulate real HTTP requests and verify the responses, including success cases and error handling.

To run all tests, use the following Maven command:

```bash
mvn test
```

## Code Formatting

This project uses [Spotless](https://github.com/diffplug/spotless) to format the code. To apply the formatting, run the following Maven command:

```bash
mvn spotless:apply
```

## Technologies Used

-   **Java 25:** The version of Java used.
-   **Spring Boot 3.x:** The framework used to build the application.
-   **Maven:** The build tool for the project.
-   **H2 Database:** An in-memory database for the example data.
-   **JUnit 5:** The testing framework for unit and integration tests.
-   **Mockito:** A mocking framework for testing.
-   **MapStruct:** A code generator for mapping between Java beans.
-   **Springdoc (Swagger UI):** For generating API documentation.
-   **Spotless:** A code formatter.
-   **Lombok:** To reduce boilerplate code.
