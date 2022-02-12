package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.BookRepository;

import java.util.List;
import java.util.UUID;

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

    @Override
    public Book createBookOnDto(BookDto bookDto) {

        String id = bookDto.getId();
        String title = bookDto.getTitle().trim();
        String authorName = bookDto.getAuthorName().trim();
        String genreTitle = bookDto.getGenreTitle().trim();

        Book book = new Book(id, title);

        Author author = authorService.getByNameOrCreate(authorName);
        book.setAuthor(author);
        Genre genre = genreService.getByTitleOrCreate(genreTitle);
        book.setGenre(genre);

        return book;
    }
}
