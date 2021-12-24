package ru.otus.homework.repository;

import ru.otus.homework.domain.Genre;

import java.util.Optional;

public interface GenreRepository {

    Genre save(Genre genre);

    Optional<Genre> getById(long id);

    Optional<Genre> getByTitle(String title);
}
