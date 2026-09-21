package com.example.bookapi.repository;

import com.example.bookapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorId(Long authorId);

    @Query("SELECT b FROM Book b WHERE b.price <= :maxPrice")
    List<Book> findBooksByMaximumPrice(@Param("maxPrice") Double maxPrice);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchBooks(@Param("keyword") String keyword);
}