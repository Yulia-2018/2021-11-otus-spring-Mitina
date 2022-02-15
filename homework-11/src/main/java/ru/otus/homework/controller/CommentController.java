package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.BookRepository;

@Controller
public class CommentController {

    private final BookRepository bookRepository;

    public CommentController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{bookId}/comments")
    public String listPage(@PathVariable("bookId") String bookId, Model model) {
        Flux<Comment> comments = bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new NotFoundException("Book " + bookId + " not exist")))
                .map(Book::getComments)
                .flatMapMany(Flux::fromIterable);
        model.addAttribute("bookId", bookId);
        model.addAttribute("comments", comments);
        return "comments";
    }
}
