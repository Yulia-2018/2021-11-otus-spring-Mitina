package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;

import java.util.UUID;

import static ru.otus.homework.controller.Util.*;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/")
    public String listPage() {
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        Mono<BookDto> bookDto = bookRepository.findById(id).switchIfEmpty(Mono.just(new Book("0"))).map(BookDto::toDto);
        model.addAttribute("bookDto", bookDto);
        Flux<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        Flux<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "edit";
    }

    @PostMapping("/edit")
    public String saveBook(@ModelAttribute("bookDto") BookDto bookDto) {

        String id = bookDto.getId();
        String title = bookDto.getTitle().trim();
        String authorName = bookDto.getAuthorName().trim();
        String genreTitle = bookDto.getGenreTitle().trim();
        Book book = new Book(id, title);

        if (!id.equals("0")) {
            getMonoBookForUpdate(book, authorName, genreTitle, bookRepository, authorRepository, genreRepository).subscribe();
        } else {
            book.setId(UUID.randomUUID().toString());
            getMonoBookForCreate(book, authorName, genreTitle, bookRepository, authorRepository, genreRepository).subscribe();
        }
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") String id) {
        bookRepository.deleteById(id).subscribe();
        return "redirect:/";
    }

    /*@GetMapping("/{bookId}/comments")
    public String listPage(@PathVariable("bookId") String bookId, Model model) {
        Book book = bookService.getById(bookId);
        List<Comment> comments = book.getComments();
        model.addAttribute("bookId", bookId);
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PostMapping("/{bookId}/comments")
    public String saveComment(@PathVariable("bookId") String bookId, @RequestParam("commentsText") List<String> commentsText) {
        Book book = bookService.getById(bookId);
        List<Comment> comments = new ArrayList<>();
        for (String text : commentsText) {
            if (!text.trim().isEmpty()) {
                comments.add(new Comment(text.trim()));
            }
        }
        book.setComments(comments);
        bookService.update(book);
        return "redirect:/";
    }*/
}
