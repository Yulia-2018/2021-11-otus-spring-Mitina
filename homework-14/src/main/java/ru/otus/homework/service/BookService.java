package ru.otus.homework.service;

import ru.otus.homework.domain.mongo.Book;
import ru.otus.homework.domain.relational.R_Book;

public interface BookService {

    R_Book convert(Book book);
}
