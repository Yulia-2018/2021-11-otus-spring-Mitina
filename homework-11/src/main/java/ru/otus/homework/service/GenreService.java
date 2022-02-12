package ru.otus.homework.service;

import ru.otus.homework.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre getById(String id);

    List<Genre> getAll();

    Genre getByTitleOrCreate(String title);
}
