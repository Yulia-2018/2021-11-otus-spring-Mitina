package ru.otus.homework.repository;

import ru.otus.homework.domain.Author;

public interface AuthorRepositoryCustom {

    void updateAuthorInBooks(Author author);

    Author getOrCreate(Author author);
}
