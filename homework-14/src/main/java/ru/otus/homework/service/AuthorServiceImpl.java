package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.mongo.Author;
import ru.otus.homework.domain.relational.R_Author;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Override
    public R_Author convert(Author author) {
        return new R_Author(author.getName());
    }
}
