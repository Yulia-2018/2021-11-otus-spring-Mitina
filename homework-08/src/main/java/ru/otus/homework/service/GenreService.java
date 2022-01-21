package ru.otus.homework.service;

import ru.otus.homework.domain.Genre;
import ru.otus.homework.exception.NotFoundException;

import java.util.List;

public interface GenreService {

    void update(Genre genre) throws NotFoundException;

    Genre getById(String id) throws NotFoundException;

    List<Genre> getAll();
}
