package ru.otus.homework.repository;

import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);

    Optional<Book> getById(long id);

    Optional<Book> getByIdWithComments(long id);

    List<Book> getAll();

    boolean deleteById(long id);
}
