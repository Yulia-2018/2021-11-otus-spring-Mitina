package ru.otus.homework.service;

import ru.otus.homework.domain.Genre;

public interface GenreService {

    Genre getOrCreate(Genre genre);
}
