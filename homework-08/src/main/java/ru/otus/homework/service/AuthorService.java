package ru.otus.homework.service;

import ru.otus.homework.domain.Author;
import ru.otus.homework.exception.NotFoundException;

import java.util.List;

public interface AuthorService {

    void update(Author author) throws NotFoundException;

    Author getById(String id) throws NotFoundException;

    List<Author> getAll();
}
