# ContactService

ContactService is a RESTful service designed to handle the creation and retrieval of contact messages. This service is used in conjunction with the PowerLog application to manage user contacts efficiently.

## Features

- Save new contacts
- Fetch all contacts
- Fetch contacts created after a specified date and time

## Technologies Used

- Spring Boot
- Spring Reactive
- R2DBC
- MySQL
- Flyway for data migration tool
- Spring Security
- Gradle for build automation
- Apache Kafka
- OpenAPI
- Lombok

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle
- MySQL (or any other SQL database)

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/hyuseinlesho/contact-service.git
    cd contact-service
    ```

2. Update the `application.yaml` file with your database connection details and other configurations:
    ```yaml
    spring:
      r2dbc:
        url: r2dbc:mysql://localhost:3306/contact_service
        username: ${DB_USERNAME}
        password: ${DB_PASSWORD}
      flyway:
        url: jdbc:mysql://localhost:3306/contact_service
        user: ${DB_USERNAME}
        password: ${DB_PASSWORD}
    ```

3. Set environment variables for your database username and password:
    ```sh
    -DB_USERNAME=
    -DB_PASSWORD=
    ```

4. Install and run Apache Kafka server

5. Build and run the application:
    ```sh
    ./gradlew bootRun
    ```

### API Documentation

This project uses springdoc-openapi to generate API documentation.

You can access the Swagger UI to explore and interact with the API by running the application and navigating to the following URL in your web browser:
    ```
    http://localhost:8081/swagger-ui.html
    ```

### Apache Kafka

- **Kafka Server Requirement:**
  - Ensure Apache Kafka is installed and running to test it locally.

- Produces messages to the Kafka topic `contact-topic` when a new contact is created.
- Configured bootstrap servers, key and value serializers in `application.yaml` file.
