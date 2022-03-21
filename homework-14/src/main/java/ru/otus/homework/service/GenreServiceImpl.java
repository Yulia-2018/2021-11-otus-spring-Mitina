package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.mongo.Genre;
import ru.otus.homework.domain.relational.R_Genre;

@Service
public class GenreServiceImpl implements GenreService {

    @Override
    public R_Genre convert(Genre genre) {
        return new R_Genre(genre.getTitle());
    }
}
