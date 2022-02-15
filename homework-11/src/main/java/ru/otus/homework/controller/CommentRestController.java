package ru.otus.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.CommentDto;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.BookRepository;

@RestController
public class CommentRestController {

    private final BookRepository bookRepository;

    public CommentRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CommentDto> getAll(@RequestParam String bookId) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new NotFoundException("Book " + bookId + " not exist")))
                .map(Book::getComments)
                .flatMapMany(Flux::fromIterable)
                .map(CommentDto::toDto);
    }
}
