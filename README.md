# MADR

<!-- Add your project logo / SVG here -->
<!-- Example: <p align="center"><img src="./docs/madr-logo.svg" width="180" alt="MADR logo"></p> -->

<p align="center">
  A study-oriented REST API built with Java and Spring Boot for managing books, novelists, and user authentication.
</p>

---

## About the Project

MADR is a backend REST API developed as a **study project** focused on practicing backend development with **Java, Spring Boot, Spring Data JPA, Spring Security, JWT authentication, relational databases, and REST API design**.

The project provides a simple library-style domain where books are associated with novelists, while authenticated users access protected resources according to their roles.

The goal is not to provide a production-ready platform, but to serve as a practical project for studying how the main pieces of a Spring Boot backend fit together.

## Technologies

<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,spring,mysql,maven,git,github&perline=7" alt="Technology stack">
  </a>
</p>

<!--
Technology cards are provided by Skill Icons.
You can change the icons in the `i=` parameter.
-->

### Main technologies used

- **Java 21**
- **Spring Boot**
- **Spring Data JPA / Hibernate**
- **Spring Security**
- **Auth0 Java JWT**
- **MySQL**
- **Maven**
- **Springdoc OpenAPI / Swagger UI**

## Features

- User registration and login
- JWT-based authentication
- Role-based authorization with `USER` and `ADMIN`
- Password hashing with BCrypt
- Book creation, retrieval, update, deletion, and search
- Novelist creation, retrieval, update, deletion, and name search
- Pagination for collection/search endpoints
- JPA relationships between books and novelists
- Centralized exception handling
- Request validation with Jakarta Validation
- OpenAPI / Swagger documentation
- Environment-based configuration for database credentials and JWT secret

## Domain Overview

The main entities are:

```text
User
 └── role: USER | ADMIN

Novelist
 └── books: Set<Book>

Book
 ├── title
 ├── year
 └── novelist -> Novelist
```

The relationship between `Book` and `Novelist` is implemented with JPA as:

- `Book -> Novelist`: `@ManyToOne`
- `Novelist -> Books`: `@OneToMany(mappedBy = "novelist")`

This was intentionally kept simple so the project can be used to study ORM relationships and entity mapping.

## Authentication and Authorization

MADR uses **Spring Security** with a stateless security configuration.

After a successful login, the API generates a JWT. Protected requests must send the token through the `Authorization` header:

```http
Authorization: Bearer <your-token>
```

The application defines two roles:

- `USER`
- `ADMIN`

The current security rules are organized as follows:

| Request | Access |
|---|---|
| `POST /v1/auth/login` | Public |
| `GET /v1/**` | `USER` or `ADMIN` |
| Other authenticated endpoints | `ADMIN` |
| `/error` | Public |

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/v1/auth/login` | Authenticate a user and return a JWT |
| `POST` | `/v1/auth/register` | Register a new user |
| `DELETE` | `/v1/auth/deleteByUsername/{username}` | Delete a user by username |
| `PATCH` | `/v1/auth/patchPasswordByUsername/{username}` | Update a user's password |

### Books

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/v1/books` | Create a book |
| `GET` | `/v1/books/{id}` | Get a book by ID |
| `GET` | `/v1/books/search` | Search books by title and year |
| `PATCH` | `/v1/books/{id}` | Update a book |
| `DELETE` | `/v1/books/{id}` | Delete a book |

### Novelists

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/v1/novelist` | Create a novelist |
| `GET` | `/v1/novelist/byId/{id}` | Get a novelist by ID |
| `GET` | `/v1/novelist/byName/{name}` | Search novelists by name |
| `PATCH` | `/v1/novelist/{id}` | Update a novelist |
| `DELETE` | `/v1/novelist/{id}` | Delete a novelist |

## Example Login

Request:

```http
POST /v1/auth/login
Content-Type: application/json

{
  "username": "your_username",
  "password": "your_password"
}
```

The returned token can then be used in protected requests:

```http
GET /v1/books/1
Authorization: Bearer <your-token>
```

## Book Search

The book search endpoint accepts pagination parameters together with the title and year:

```http
GET /v1/books/search?page=0&size=10&title=example&year=2024
```

The project uses Spring Data's `Page` abstraction for paginated responses.

## Getting Started

### Prerequisites

Make sure you have installed:

- Java 21
- Maven (or use the included Maven Wrapper)
- MySQL

### 1. Clone the repository

```bash
git clone <your-repository-url>
cd madr
```

### 2. Configure the environment

The application reads the database credentials from environment variables:

```text
DATABASE_USERNAME=your_mysql_username
DATABASE_PASSWORD=your_mysql_password
JWT_KEY=your_jwt_secret
```

The application is configured to connect to:

```text
jdbc:mysql://localhost:3306/madr?createDatabaseIfNotExist=true
```

The database is created automatically by MySQL when the configured credentials have permission to do so.

### 3. Run the application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Or with Maven installed:

```bash
mvn spring-boot:run
```

## API Documentation

The project includes Springdoc OpenAPI support, making the API available through Swagger UI while the application is running.

Default Swagger UI path:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

## Project Structure

```text
src/
├── main/
│   ├── java/com/antony/madr/
│   │   ├── book/
│   │   │   ├── BookController.java
│   │   │   ├── BookEntity.java
│   │   │   ├── BookService.java
│   │   │   ├── IBookRepository.java
│   │   │   └── DTOs
│   │   ├── novelist/
│   │   │   ├── NovelistController.java
│   │   │   ├── NovelistEntity.java
│   │   │   ├── NovelistService.java
│   │   │   ├── INovelistRepository.java
│   │   │   └── DTOs
│   │   ├── users/
│   │   │   ├── UserController.java
│   │   │   ├── UsersEntity.java
│   │   │   ├── UserService.java
│   │   │   └── DTOs
│   │   ├── infra/
│   │   │   ├── exceptions/
│   │   │   └── security/
│   │   ├── utils/
│   │   └── MadrApplication.java
│   └── resources/
│       └── application.yaml
└── test/
    └── java/com/antony/madr/
        └── MadrApplicationTests.java
```

## Error Handling

The project uses a centralized exception handler with `@ControllerAdvice` to convert application exceptions into HTTP responses.

Examples include:

- `404 Not Found` for resources that do not exist
- `409 Conflict` for duplicated resources
- `403 Forbidden` for authorization failures
- Authentication-related errors returned as API error responses

This approach keeps controllers and services focused on application behavior while keeping HTTP error handling in one place.

## ORM Relationships

Working with ORM relationships can introduce problems around ownership, foreign keys, cascading operations, lazy loading, and entity state.

In this project, the main relationship is between **novelists and books**:

```text
Novelist 1 ─────────── * Book
```

The `Book` entity owns the relationship through the `novelist` field, while `Novelist` exposes the collection through `mappedBy = "novelist"`.

A useful rule when working on this project is to identify **which entity owns the foreign key** before changing the relationship mapping.

## Study Project

This repository is intentionally presented as a **learning project**. The implementation focuses on practicing backend concepts, understanding how the framework pieces interact, and experimenting with common REST API patterns.

It should be treated as an evolving study project rather than a finished production system.

> **AI disclosure:** No AI-generated code was used in this project.

## License

This project is intended for educational and study purposes.
---

<p align="center">
  Made for learning backend development with Java and Spring Boot.
</p>
