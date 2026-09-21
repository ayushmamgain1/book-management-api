package com.example.bookapi.controller;

import com.example.bookapi.entity.Book;
import com.example.bookapi.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(
            @Valid @RequestBody Book book) {

        Book createdBook = bookService.createBook(book);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody Book book) {

        return ResponseEntity.ok(
                bookService.updateBook(id, book)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                bookService.searchBooks(keyword)
        );
    }

    @GetMapping("/price")
    public ResponseEntity<List<Book>> getBooksByMaximumPrice(
            @RequestParam Double maxPrice) {

        return ResponseEntity.ok(
                bookService.getBooksByMaximumPrice(maxPrice)
        );
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Book>> getBooksByAuthor(
            @PathVariable Long authorId) {

        return ResponseEntity.ok(
                bookService.getBooksByAuthor(authorId)
        );
    }
}