v1.0.1

# 🎬 Movie Rental API (Spring Boot Backend)

This is a RESTful backend for managing a movie rental system. It supports basic CRUD operations for movies and customers, user registration/login with JWT-based authentication, and role-based access control.

## 🛠️ Tech Stack

- Java 21(temurin)
- Spring Boot
- Spring Security + JWT
- Spring Data JPA (H2)
- Maven
- JUnit 5
- Mockito (For Tests)
- GitHub Actions CI

## 🚀 Features

- 🧾 User registration and login (JWT)
- 🔐 Role-based access control (Superadmin, Admin, Consumer)
- 🎥 CRUD operations for movies
- 👥 Customer management
- 🧪 Unit and integration tests
- 📄 H2 in-memory database for development/testing

## ⚙️ Setup Instructions

### Prerequisites
- JDK 21+
- Maven 3.6+ (3.9+ Recommended to avoid compatibility issues)
- (Optional) Postman or curl for API testing

### Run the Application

```bash
git clone https://github.com/AxZethh/Movie-Rental.git
cd Movie-Rental
mvn clean install
mvn spring-boot:run
```
- The application will run at http://localhost:8080
## 🧪 Running Tests

Tests use JUnit 5 and Mockito for mocking dependencies to isolate unit tests.

Run tests with:

```bash
mvn test
```