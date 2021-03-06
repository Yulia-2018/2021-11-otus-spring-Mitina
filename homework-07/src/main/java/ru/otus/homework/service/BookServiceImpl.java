package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.BookRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
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
        getById(book.getId());
        addAuthorAndGenre(book);
        bookRepository.save(book);
    }

    @Override
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book " + id + " not exist"));
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        Book book = getById(id);
        bookRepository.delete(book);
    }

    private void addAuthorAndGenre(Book book) {
        Author author = authorService.getOrCreate(book.getAuthor());
        Genre genre = genreService.getOrCreate(book.getGenre());
        book.setAuthor(author);
        book.setGenre(genre);
    }
}
