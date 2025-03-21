# CTX Chemical APIs

## Overview
This project is a RESTful API for retrieving chemical structure data and related information.

## Technologies Used
- **Java**: Programming language used for the development of the application.
- **Spring Boot**: Framework used to create stand-alone, production-grade Spring-based applications.
- **Spring Data JPA**: Part of the Spring Data family, used to simplify data access and persistence.
- **Spring Data REST**: Used to expose Spring Data repositories as RESTful web services.
- **PostgreSQL**: Relational database used for storing chemical data.
- **H2 Database**: In-memory database used for testing purposes.
- **Maven**: Build automation tool used for managing project dependencies and build lifecycle.
- **Lombok**: Java library used to reduce boilerplate code.
- **MapStruct**: Code generator used to simplify the implementation of mappings between Java bean types.
- **Testcontainers**: Java library used for integration testing with Docker containers.
- **OPSIN**: Library used for converting chemical names to structures.
- **Swagger**: Used for API documentation and testing.

## Configuration
The application configuration is managed through YAML files. The main configuration file is `application.yml`, and environment-specific configurations are in files like `application-local.yml`.

## Build and Deployment
The project uses GitHub Actions for continuous integration and deployment. The build and deployment process is defined in the `.github/workflows/gh-packages-deployment.yml` file.

## Getting Started
To build and run the project locally, use the following Maven commands:

```sh
mvn clean package
mvn spring-boot:run