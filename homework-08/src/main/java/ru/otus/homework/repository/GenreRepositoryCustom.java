package ru.otus.homework.repository;

import ru.otus.homework.domain.Genre;

public interface GenreRepositoryCustom {

    Genre getOrCreate(Genre genre);
}
