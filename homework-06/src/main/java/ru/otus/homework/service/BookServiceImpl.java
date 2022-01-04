package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.BookRepository;

import java.util.List;
import java.util.Optional;

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
        return bookRepository.getById(id).orElseThrow(() -> new NotFoundException("Book " + id + " not exist"));
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
        Author author = authorService.getOrCreate(book.getAuthor());
        Genre genre = genreService.getOrCreate(book.getGenre());
        book.setAuthor(author);
        book.setGenre(genre);
    }
}
