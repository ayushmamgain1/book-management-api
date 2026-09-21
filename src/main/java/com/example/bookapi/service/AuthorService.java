package com.example.bookapi.service;

import com.example.bookapi.entity.Author;
import com.example.bookapi.exception.ResourceNotFoundException;
import com.example.bookapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existingAuthor = getAuthorById(id);

        existingAuthor.setName(updatedAuthor.getName());
        existingAuthor.setEmail(updatedAuthor.getEmail());

        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        authorRepository.delete(author);
    }
}