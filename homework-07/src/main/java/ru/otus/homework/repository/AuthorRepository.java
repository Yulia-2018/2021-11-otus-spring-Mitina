package ru.otus.homework.repository;

import ru.otus.homework.domain.Author;

import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);

    Optional<Author> getById(long id);

    Optional<Author> getByName(String name);
}
