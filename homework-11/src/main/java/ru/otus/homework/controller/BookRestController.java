package ru.otus.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;

import static ru.otus.homework.controller.Util.getMonoBookForCreate;
import static ru.otus.homework.controller.Util.getMonoBookForUpdate;

@RestController
public class BookRestController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    public BookRestController(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BookDto> getAll() {
        return bookRepository.findAll().map(BookDto::toDto);
    }

    @GetMapping(value = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BookDto> get(@PathVariable String id) {
        return bookRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException("Book " + id + " not exist"))).map(BookDto::toDto);
    }

    @DeleteMapping("/books/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return bookRepository.deleteById(id);
    }

    @PutMapping(value = "/books/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> update(@RequestBody Book book, @PathVariable String id) {
        String authorName = book.getAuthor().getName();
        String genreTitle = book.getGenre().getTitle();
        book.setId(id);
        return getMonoBookForUpdate(book, authorName, genreTitle, bookRepository, authorRepository, genreRepository).then();
    }

    @PostMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> create(@RequestBody Book book) {
        String authorName = book.getAuthor().getName();
        String genreTitle = book.getGenre().getTitle();
        return getMonoBookForCreate(book, authorName, genreTitle, bookRepository, authorRepository, genreRepository);
    }
}
