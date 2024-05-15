# Spring Boot 3.0 Security with JWT Implementation
This project demonstrates the implementation of security using Spring Boot 3.0 and JSON Web Tokens (JWT). It includes the following features:

## Features
* User registration and login with JWT authentication
* Password encryption using BCrypt
* Role-based authorization with Spring Security
* Account activation via email with six-digit verification code
* API documentation with Swagger

## Technologies
* Spring Boot 3.2.5
* Spring Security 6
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* Swagger

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+

To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/Ahmed-Ibrahim99/spring-boot-3-jwt-security.git`
* Navigate to the project directory: cd spring-boot-security-jwt
* Add database "spring_security_jwt" to postgres
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run

-> The application will be available at http://localhost:8080.

For API documentation, Swagger is utilized. After running the project, you can access the Swagger UI at http://localhost:8080/swagger-ui.html.