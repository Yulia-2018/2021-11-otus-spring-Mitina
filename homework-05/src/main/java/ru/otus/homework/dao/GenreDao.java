package ru.otus.homework.dao;

import ru.otus.homework.domain.Genre;

public interface GenreDao {

    Genre insert(Genre genre);

    Genre getById(long id);

    Genre getByTitle(String title);
}
