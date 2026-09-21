package com.example.bookapi.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Book title cannot be null")
    @Size(min = 2, max = 200, message = "Book title must be between 2 and 200 characters")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "ISBN cannot be null")
    @Size(min = 10, max = 20, message = "ISBN must be between 10 and 20 characters")
    @Column(nullable = false, unique = true)
    private String isbn;

    @NotNull(message = "Price cannot be null")
    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference
    private Author author;

    public Book() {
    }

    public Book(String title, String isbn, Double price, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}