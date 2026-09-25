# Book Management API

A RESTful Book Management API built with **Spring Boot**, **Spring Data JPA**, **PostgreSQL**, and **Spring Security with JWT authentication**.

The application provides CRUD operations for books and authors, along with stateless JWT-based authentication and authorization.

---

## 🚀 Features

### Book Management

* Create a book
* Get all books
* Get a book by ID
* Update a book
* Delete a book
* Search books by keyword
* Find books by maximum price
* Find books by author

### Author Management

* Create an author
* Get all authors
* Get an author by ID
* Update an author
* Delete an author

### Authentication & Security

* User registration
* User login
* BCrypt password encryption
* Custom `UserDetailsService`
* JWT token generation
* JWT token validation
* JWT authentication filter using `OncePerRequestFilter`
* Stateless session management
* Protected API endpoints
* Custom JSON authentication error responses
* Duplicate username/email validation
* Invalid login credential handling

---

## 🛠️ Technologies Used

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* PostgreSQL
* JWT / JSON Web Token
* JJWT
* Jakarta Bean Validation
* Maven
* Postman

---

## 📁 Project Structure

```text
src
└── main
    └── java
        └── com.example.bookapi
            │
            ├── config
            │   └── SecurityConfig.java
            │
            ├── controller
            │   ├── AuthController.java
            │   ├── BookController.java
            │   └── AuthorController.java
            │
            ├── dto
            │   ├── LoginRequest.java
            │   └── RegisterRequest.java
            │
            ├── entity
            │   ├── User.java
            │   ├── Book.java
            │   └── Author.java
            │
            ├── exception
            │   ├── UsernameAlreadyExistsException.java
            │   ├── EmailAlreadyExistsException.java
            │   ├── InvalidCredentialsException.java
            │   └── GlobalExceptionHandler.java
            │
            ├── repository
            │   ├── UserRepository.java
            │   ├── BookRepository.java
            │   └── AuthorRepository.java
            │
            ├── security
            │   ├── CustomUserDetailsService.java
            │   ├── JwtService.java
            │   ├── JwtAuthenticationFilter.java
            │   └── CustomAuthenticationEntryPoint.java
            │
            └── service
                ├── AuthService.java
                ├── BookService.java
                └── AuthorService.java
```

---

## 🔐 Authentication Flow

The application uses stateless JWT authentication.

```text
Client
  │
  │ Register
  ▼
/api/auth/register
  │
  │ BCrypt password hashing
  ▼
PostgreSQL
```

For login:

```text
Client
  │
  │ username + password
  ▼
/api/auth/login
  │
  ▼
AuthenticationManager
  │
  ▼
CustomUserDetailsService
  │
  ▼
BCrypt password verification
  │
  ▼
JWT Token
  │
  ▼
Client
```

For protected endpoints:

```text
Client
  │
  │ Authorization: Bearer <JWT>
  ▼
JwtAuthenticationFilter
  │
  ▼
JWT validation
  │
  ▼
SecurityContext
  │
  ▼
Protected Controller
```

---

## 🔑 API Endpoints

### Authentication

| Method | Endpoint             | Authentication |
| ------ | -------------------- | -------------- |
| POST   | `/api/auth/register` | Public         |
| POST   | `/api/auth/login`    | Public         |

### Books

| Method | Endpoint                              | Authentication |
| ------ | ------------------------------------- | -------------- |
| GET    | `/api/books`                          | JWT Required   |
| GET    | `/api/books/{id}`                     | JWT Required   |
| POST   | `/api/books`                          | JWT Required   |
| PUT    | `/api/books/{id}`                     | JWT Required   |
| DELETE | `/api/books/{id}`                     | JWT Required   |
| GET    | `/api/books/search?keyword={keyword}` | JWT Required   |
| GET    | `/api/books/price?maxPrice={price}`   | JWT Required   |
| GET    | `/api/books/author/{authorId}`        | JWT Required   |

### Authors

| Method | Endpoint            | Authentication |
| ------ | ------------------- | -------------- |
| GET    | `/api/authors`      | JWT Required   |
| GET    | `/api/authors/{id}` | JWT Required   |
| POST   | `/api/authors`      | JWT Required   |
| PUT    | `/api/authors/{id}` | JWT Required   |
| DELETE | `/api/authors/{id}` | JWT Required   |

---

## 📝 Registration

### Request

```http
POST /api/auth/register
Content-Type: application/json
```

```json
{
    "username": "ayush",
    "email": "ayush@example.com",
    "password": "password123"
}
```

### Response

```json
{
    "id": 1,
    "username": "ayush",
    "email": "ayush@example.com",
    "role": "USER"
}
```

Passwords are stored using BCrypt hashing and are never returned in API responses.

---

## 🔓 Login

### Request

```http
POST /api/auth/login
Content-Type: application/json
```

```json
{
    "username": "ayush",
    "password": "password123"
}
```

### Response

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

The returned JWT must be sent with protected requests.

---

## 🔒 Using JWT Authentication

Add the following HTTP header:

```http
Authorization: Bearer <JWT_TOKEN>
```

Example:

```http
GET /api/books
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

Requests to protected endpoints without a valid JWT return:

```json
{
    "status": 401,
    "error": "Unauthorized",
    "message": "Authentication is required",
    "path": "/api/books"
}
```

---

## ❌ Authentication Error Handling

### Invalid Login

If an incorrect username or password is provided:

```json
{
    "status": 401,
    "error": "Unauthorized",
    "message": "Invalid username or password"
}
```

### Duplicate Username

```json
{
    "status": 400,
    "error": "Bad Request",
    "message": "Username already taken"
}
```

### Duplicate Email

```json
{
    "status": 400,
    "error": "Bad Request",
    "message": "Email already registered"
}
```

---

## ⚙️ Configuration

Create the required environment variables before starting the application.

### PostgreSQL

The application expects:

```text
DB_PASSWORD=your_postgresql_password
```

### JWT

The application also requires:

```text
JWT_SECRET=your_long_random_jwt_secret
```

The JWT secret should be sufficiently long for HMAC signing.

Do **not** commit real passwords or JWT secrets to GitHub.

---

## 🗄️ Database Configuration

The application uses PostgreSQL.

Example configuration:

```properties
spring.application.name=book-management-api

spring.datasource.url=jdbc:postgresql://localhost:5432/book_management
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Create the database before starting the application:

```sql
CREATE DATABASE book_management;
```

---

## ▶️ Running the Application

### 1. Clone the repository

```bash
git clone https://github.com/ayushmamgain1/book-management-api
cd book-management-api
```

### 2. Configure environment variables

PowerShell:

```powershell
$env:DB_PASSWORD="your_postgresql_password"
$env:JWT_SECRET="your_long_random_jwt_secret"
```

### 3. Run tests

```powershell
.\mvnw.cmd clean test
```

### 4. Start the application

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

---

## 🧪 Postman Testing

The APIs were tested using Postman.

The authentication flow tested includes:

1. User registration
2. Duplicate username handling
3. Duplicate email handling
4. Successful login
5. Invalid login credentials
6. JWT generation
7. Accessing protected endpoints without a token
8. Accessing protected endpoints with a valid JWT
9. Book CRUD operations
10. Book search/filter endpoints
11. Author CRUD operations

### Authentication Test Flow

```text
Register
   ↓
Login
   ↓
Receive JWT
   ↓
Set Bearer Token in Postman
   ↓
Access protected APIs
```

A Postman collection is included with the project for API testing.

---

## 🧪 Security Architecture

The main security components are:

### `SecurityConfig`

Configures:

* Public authentication endpoints
* Protected API endpoints
* Stateless sessions
* JWT filter
* Authentication entry point
* BCrypt password encoder

### `CustomUserDetailsService`

Loads users from the PostgreSQL database and provides them to Spring Security.

### `JwtService`

Responsible for:

* JWT generation
* JWT parsing
* Username extraction
* Token expiration validation
* Signature validation

### `JwtAuthenticationFilter`

Extends:

```java
OncePerRequestFilter
```

It extracts the JWT from:

```http
Authorization: Bearer <token>
```

and authenticates the user if the token is valid.

### `CustomAuthenticationEntryPoint`

Returns JSON responses for unauthenticated requests.

---

## 🔐 Security Practices

* Passwords are hashed using BCrypt.
* Passwords are not returned through API responses.
* JWT authentication is stateless.
* Database passwords are stored through environment variables.
* JWT secrets are stored through environment variables.
* Protected endpoints require authentication.
* Invalid credentials do not reveal whether a username exists.

---

## 📦 Build

Build the project using:

```powershell
.\mvnw.cmd clean package
```

Run tests using:

```powershell
.\mvnw.cmd clean test
```

---

## 👨‍💻 Author

**Ayush Mamgain**

Book Management API — Internship Project
