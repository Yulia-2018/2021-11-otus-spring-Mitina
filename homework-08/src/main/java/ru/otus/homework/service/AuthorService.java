package ru.otus.homework.service;

import ru.otus.homework.domain.Author;
import ru.otus.homework.exception.NotFoundException;

public interface AuthorService {

    void update(Author author) throws NotFoundException;
}
