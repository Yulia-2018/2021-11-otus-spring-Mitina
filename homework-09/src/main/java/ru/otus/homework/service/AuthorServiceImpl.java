package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Author;
import ru.otus.homework.repository.AuthorRepository;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getOrCreate(Author author) {
        String name = author.getName();
        Optional<Author> authorFromBase = authorRepository.getByName(name);
        return (authorFromBase.isEmpty()) ? authorRepository.save(author) : authorFromBase.get();
    }
}
