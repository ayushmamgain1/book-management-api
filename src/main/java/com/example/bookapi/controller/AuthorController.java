package com.example.bookapi.controller;

import com.example.bookapi.entity.Author;
import com.example.bookapi.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(
            @Valid @RequestBody Author author) {

        Author createdAuthor = authorService.createAuthor(author);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody Author author) {

        return ResponseEntity.ok(
                authorService.updateAuthor(id, author)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {

        authorService.deleteAuthor(id);

        return ResponseEntity.noContent().build();
    }
}