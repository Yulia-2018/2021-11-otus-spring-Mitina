package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    @Override
    public Book insert(Book book) {
        addAuthorAndGenre(book);
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void update(Book book) {
        long id = book.getId();
        Optional<Book> bookFromBase = bookRepository.getById(id);
        if (bookFromBase.isEmpty()) {
            throw new NotFoundException("Book " + id + " not exist");
        }
        addAuthorAndGenre(book);
        bookRepository.save(book);
    }

    @Override
    public Book getById(long id) {
        Optional<Book> book = bookRepository.getById(id);
        if (book.isEmpty()) {
            throw new NotFoundException("Book " + id + " not exist");
        }
        return book.get();
    }

    @Transactional(readOnly = true)
    @Override
    public Book getByIdWithComments(long id) throws NotFoundException {
        Optional<Book> bookWithComments = bookRepository.getByIdWithComments(id);
        if (bookWithComments.isEmpty()) {
            throw new NotFoundException("Book " + id + " not exist");
        }
        return bookWithComments.get();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        if (!bookRepository.deleteById(id)) {
            throw new NotFoundException("Book " + id + " not exist");
        }
    }

    private void addAuthorAndGenre(Book book) {
        Author author = insertAuthorIfNotExist(book.getAuthor());
        Genre genre = insertGenreIfNotExist(book.getGenre());
        book.setAuthor(author);
        book.setGenre(genre);
    }

    private Author insertAuthorIfNotExist(Author author) {
        String name = author.getName();
        Optional<Author> authorFromBase = authorRepository.getByName(name);
        return (authorFromBase.isEmpty()) ? authorRepository.save(author) : authorFromBase.get();
    }

    private Genre insertGenreIfNotExist(Genre genre) {
        String title = genre.getTitle();
        Optional<Genre> genreFromBase = genreRepository.getByTitle(title);
        return (genreFromBase.isEmpty()) ? genreRepository.save(genre) : genreFromBase.get();
    }
}