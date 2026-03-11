# BCNC Code Test


This is a REST service that provides a priceDomain query endpoint. It allows you to find the applicable priceDomain for a product of a specific brand on a given date.

## Features

- REST API to find the applicable priceDomain for a product.
- Hexagonal architecture for a clean and maintainable design.
- API documentation with Swagger UI.
- Unit and integration tests to ensure code quality.
- Code formatting with Spotless.

## Prerequisites

- Java 25
- Maven 3.9.x

## Build and Run

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

Retrieves the applicable priceDomain for a given brand, product, and date.

**Parameters:**

| Name      | Type          | Description                                      | Example                |
| --------- | ------------- | ------------------------------------------------ | ---------------------- |
| `brandId`   | `Integer`     | The identifier of the brand.                     | `1`                    |
| `productId` | `Integer`     | The identifier of the product.                   | `35455`                |
| `date`      | `LocalDateTime` | The date for which to find the applicable priceDomain. | `2020-06-14T10:00:00` |

**Example Request:**

```http
GET /bcnc-test/prices?brandId=1&productId=35455&date=2020-06-14T10:00:00
```

**Example Response (200 OK):**

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "priceDomain": 35.5
}
```

**Responses:**

| Code | Description       |
| ---- | ----------------- |
| 200  | Price found       |
| 400  | Invalid params    |
| 404  | Price not found   |

## Project Structure

The project follows a hexagonal architecture, which separates the application's core logic from the infrastructure details.

-   `domain`: Contains the core business model.
-   `application`: Contains the application layer, including DTOs, service and mappers.
-   `infrastructure`: Contains the infrastructure layer, including the database entities, repositories, controller, and mappers.

## Code Formatting

This project uses [Spotless](https://github.com/diffplug/spotless) to format the code. To apply the formatting, run the following Maven command:

```bash
mvn spotless:apply
```

## Technologies Used

-   **Java 25**
-   **Spring Boot 3.5.11**
-   **Maven**
-   **H2 Database**
-   **JUnit 5**
-   **Mockito**
-   **MapStruct 1.6.3**
-   **Springdoc (Swagger UI) 2.8.16**
-   **Spotless 3.3.0**
-   **Lombok 1.18.42**

## Testing

To run the tests, use the following Maven command:

```bash
mvn test
```
