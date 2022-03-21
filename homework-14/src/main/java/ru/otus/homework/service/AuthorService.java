package ru.otus.homework.service;

import ru.otus.homework.domain.mongo.Author;
import ru.otus.homework.domain.relational.R_Author;

public interface AuthorService {

    R_Author convert(Author author);
}
