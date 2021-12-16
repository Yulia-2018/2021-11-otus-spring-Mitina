package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.domain.Book;
import ru.otus.homework.exception.NotFoundException;

import java.util.List;

@Service
public class BookServiceJdbc implements BookService {

    private final BookDao dao;

    public BookServiceJdbc(BookDao dao) {
        this.dao = dao;
    }

    @Override
    public Book insert(Book book) {
        return dao.insert(book);
    }

    @Override
    public void update(Book book) {
        if (dao.update(book) == null) {
            throw new NotFoundException("Book " + book.getId() + " not exist");
        }
    }

    @Override
    public Book getById(long id) {
        Book book = dao.getById(id);
        if (book == null) {
            throw new NotFoundException("Book " + id + " not exist");
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        return dao.getAll();
    }

    @Override
    public void deleteById(long id) {
        if (!dao.deleteById(id)) {
            throw new NotFoundException("Book " + id + " not exist");
        }
    }
}
