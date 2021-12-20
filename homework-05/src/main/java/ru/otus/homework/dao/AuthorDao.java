package ru.otus.homework.dao;

import ru.otus.homework.domain.Author;

public interface AuthorDao {

    Author insert(Author author);

    Author getById(long id);

    Author getByName(String name);
}
