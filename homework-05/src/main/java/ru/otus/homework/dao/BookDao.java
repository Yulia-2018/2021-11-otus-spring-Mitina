package ru.otus.homework.dao;

import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookDao {

    Book insert(Book book);

    Book update(Book book);

    Book getById(long id);

    List<Book> getAll();

    boolean deleteById(long id);
}
