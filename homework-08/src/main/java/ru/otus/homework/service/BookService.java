package ru.otus.homework.service;

import ru.otus.homework.domain.Book;
import ru.otus.homework.exception.NotFoundException;

import java.util.List;

public interface BookService {

    Book insert(Book book);

    void update(Book book) throws NotFoundException;

    Book getById(String id) throws NotFoundException;

    List<Book> getAll();

    void deleteById(String id) throws NotFoundException;
}
