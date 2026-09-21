# Book Management REST API

A RESTful Book Management API built using **Spring Boot, Spring Data JPA, Hibernate, and PostgreSQL**.

This project demonstrates CRUD operations, JPA entity relationships, custom repository queries, request validation, service-layer architecture, and exception handling.

## Technologies Used

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* PostgreSQL
* Jakarta Validation
* Maven
* Postman
* Git & GitHub

## Project Features

* Create, read, update, and delete authors
* Create, read, update, and delete books
* PostgreSQL database persistence
* JPA/Hibernate entity mapping
* One-to-many relationship between authors and books
* Many-to-one relationship between books and authors
* Spring Data JPA repositories
* Custom JPQL queries using `@Query`
* Search books by title
* Filter books by maximum price
* Find books by author
* Jakarta Bean Validation
* Global exception handling
* RESTful API endpoints
* Proper HTTP status codes

## Project Structure

```text
book-management-api/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/bookapi/
│       │       ├── controller/
│       │       │   ├── AuthorController.java
│       │       │   └── BookController.java
│       │       │
│       │       ├── entity/
│       │       │   ├── Author.java
│       │       │   └── Book.java
│       │       │
│       │       ├── repository/
│       │       │   ├── AuthorRepository.java
│       │       │   └── BookRepository.java
│       │       │
│       │       ├── service/
│       │       │   ├── AuthorService.java
│       │       │   └── BookService.java
│       │       │
│       │       ├── exception/
│       │       │   ├── ResourceNotFoundException.java
│       │       │   └── GlobalExceptionHandler.java
│       │       │
│       │       └── BookManagementApplication.java
│       │
│       └── resources/
│           └── application.properties
│
├── .gitignore
├── pom.xml
└── README.md
```

## Database Configuration

This application uses **PostgreSQL**.

Create a PostgreSQL database named:

```text
book_management
```

The application uses the default PostgreSQL port:

```text
5432
```

Configure the database connection in `application.properties`.

For security, the database password should be provided through an environment variable instead of being stored directly in the source code.

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/book_management
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Set the environment variable before running the application.

### Windows PowerShell

```powershell
$env:DB_PASSWORD="your_postgresql_password"
```

## Running the Application

### 1. Clone the repository

```bash
git clone https://github.com/ayushmamgain1/book-management-api
```

### 2. Open the project

Open the project in IntelliJ IDEA or another Java IDE.

### 3. Configure PostgreSQL

Make sure PostgreSQL is running and the `book_management` database exists.

### 4. Configure the database password

Set the `DB_PASSWORD` environment variable.

### 5. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Or run:

```text
BookManagementApplication.java
```

from your IDE.

The application starts on:

```text
http://localhost:8080
```

## API Endpoints

### Author Endpoints

| Method | Endpoint            | Description         |
| ------ | ------------------- | ------------------- |
| GET    | `/api/authors`      | Get all authors     |
| GET    | `/api/authors/{id}` | Get author by ID    |
| POST   | `/api/authors`      | Create a new author |
| PUT    | `/api/authors/{id}` | Update an author    |
| DELETE | `/api/authors/{id}` | Delete an author    |

### Book Endpoints

| Method | Endpoint                         | Description                     |
| ------ | -------------------------------- | ------------------------------- |
| GET    | `/api/books`                     | Get all books                   |
| GET    | `/api/books/{id}`                | Get book by ID                  |
| POST   | `/api/books`                     | Create a new book               |
| PUT    | `/api/books/{id}`                | Update a book                   |
| DELETE | `/api/books/{id}`                | Delete a book                   |
| GET    | `/api/books/search?keyword=java` | Search books by title           |
| GET    | `/api/books/price?maxPrice=500`  | Find books within maximum price |
| GET    | `/api/books/author/{authorId}`   | Get books by author             |

## Sample API Requests

### Create Author

**POST**

```text
http://localhost:8080/api/authors
```

Request body:

```json
{
  "name": "J.K. Rowling",
  "email": "jkrowling@example.com"
}
```

### Create Book

**POST**

```text
http://localhost:8080/api/books
```

Request body:

```json
{
  "title": "Harry Potter and the Philosopher's Stone",
  "isbn": "9780747532699",
  "price": 599.0,
  "author": {
    "id": 1
  }
}
```

### Search Books

**GET**

```text
http://localhost:8080/api/books/search?keyword=Harry
```

### Filter by Price

**GET**

```text
http://localhost:8080/api/books/price?maxPrice=600
```

### Find Books by Author

**GET**

```text
http://localhost:8080/api/books/author/1
```

## Entity Relationships

The application contains two main entities:

### Author

An author can have multiple books.

```text
Author 1 ─────────── * Book
```

This is implemented using:

```java
@OneToMany(mappedBy = "author")
```

### Book

Each book belongs to one author.

This is implemented using:

```java
@ManyToOne
@JoinColumn(name = "author_id")
```

## Validation

The API uses Jakarta Bean Validation to validate incoming request data.

Examples include:

```java
@NotNull
@Size
@Email
```

For example, author email addresses are validated using:

```java
@Email
```

and author names are validated using:

```java
@NotNull
@Size(min = 2, max = 100)
```

Invalid requests return an HTTP `400 Bad Request` response.

## Custom Repository Queries

The project uses Spring Data JPA repositories and custom JPQL queries.

Example:

```java
@Query("SELECT b FROM Book b WHERE b.price <= :maxPrice")
List<Book> findBooksByMaximumPrice(@Param("maxPrice") Double maxPrice);
```

Another query provides title-based searching:

```java
@Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<Book> searchBooks(@Param("keyword") String keyword);
```

## Exception Handling

The application contains a global exception handler using:

```java
@RestControllerAdvice
```

It handles:

* Resource not found errors
* Validation errors

For example, requesting a book that does not exist returns:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Book not found with id: 999"
}
```

## Testing

The API was tested using **Postman**.

Tested functionality includes:

* Author creation
* Author retrieval
* Author update
* Author deletion
* Book creation
* Book retrieval
* Book update
* Book deletion
* Book search
* Price filtering
* Author-based book filtering
* Validation errors
* Resource-not-found handling
* JPA author-book relationship

## Internship Requirements Covered

| Requirement        | Implementation                     |
| ------------------ | ---------------------------------- |
| Spring Boot        | Spring Boot application            |
| REST API           | `@RestController`                  |
| GET                | Implemented                        |
| POST               | Implemented                        |
| PUT                | Implemented                        |
| DELETE             | Implemented                        |
| JPA Entity         | `@Entity`, `@Table`                |
| One-to-Many        | `Author → Books`                   |
| Many-to-One        | `Book → Author`                    |
| JpaRepository      | Author & Book repositories         |
| Custom Queries     | `@Query`                           |
| Validation         | `@NotNull`, `@Size`, `@Email`      |
| Service Layer      | AuthorService & BookService        |
| Exception Handling | GlobalExceptionHandler             |
| PostgreSQL         | Configured as persistence database |

## Author

**Ayush Mamgain**

Java / Spring Boot Developer

---

## License

This project was created for educational and internship purposes.