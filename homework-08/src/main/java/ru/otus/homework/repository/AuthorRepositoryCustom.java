package ru.otus.homework.repository;

import ru.otus.homework.domain.Author;

public interface AuthorRepositoryCustom {

    Author getOrCreate(Author author);
}
