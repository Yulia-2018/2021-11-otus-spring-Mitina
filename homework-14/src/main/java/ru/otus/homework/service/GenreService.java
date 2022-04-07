package ru.otus.homework.service;

import ru.otus.homework.domain.mongo.Genre;
import ru.otus.homework.domain.relational.R_Genre;

public interface GenreService {

    R_Genre convert(Genre genre);
}
