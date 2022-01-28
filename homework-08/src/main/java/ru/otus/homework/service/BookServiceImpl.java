package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;

import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Book insert(Book book) {
        addAuthorAndGenre(book);
        book.setId(UUID.randomUUID().toString());
        return bookRepository.save(book);
    }

    @Override
    public void update(Book book) {
        getById(book.getId());
        addAuthorAndGenre(book);
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

    private void addAuthorAndGenre(Book book) {
        Author author = authorRepository.getOrCreate(book.getAuthor());
        Genre genre = genreRepository.getOrCreate(book.getGenre());
        book.setAuthor(author);
        book.setGenre(genre);
    }
}
