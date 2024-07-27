# ContactService

ContactService is a RESTful service designed to handle the creation and retrieval of contact messages. This service is used in conjunction with the PowerLog application to manage user contacts efficiently.

## Features

- Save new contact messages
- Fetch contact messages created since a specified date and time

## Technologies Used

- Spring Boot
- RESTful API
- MySQL
- Hibernate
- Spring Data JPA
- Spring Security
- Gradle for build automation

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
      application:
        name: ContactService

      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/contact_service?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
        username: ${DB_USERNAME}
        password: ${DB_PASSWORD}

      jpa:
        properties:
          hibernate:
            format_sql: true
        hibernate:
          ddl-auto: update
        open-in-view: false

    logging:
      level:
        org: WARN
        blog: WARN
        org.hibernate.SQL: DEBUG
        org.hibernate.type.descriptor.sql.BasicBinder: TRACE

    server:
      port: 8081
    ```

3. Set environment variables for your database username and password:
    ```sh
    export DB_USERNAME=your-username
    export DB_PASSWORD=your-password
    ```

4. Build and run the application:
    ```sh
    ./gradlew bootRun
    ```
