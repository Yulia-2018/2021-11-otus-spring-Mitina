package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Book;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.BookRepository;

import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book insert(Book book) {
        book.setId(UUID.randomUUID().toString());
        return bookRepository.save(book);
    }

    @Override
    public void update(Book book) {
        getById(book.getId());
        bookRepository.save(book);
    }

    @Override
    public Book getById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book " + id + " not exist"));
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        Book book = getById(id);
        bookRepository.delete(book);
    }
}
