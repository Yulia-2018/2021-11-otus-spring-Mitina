package ru.otus.homework.repository;

import ru.otus.homework.domain.Genre;

public interface GenreRepositoryCustom {

    void updateGenreInBooks(Genre genre);

    Genre getOrCreate(Genre genre);
}
