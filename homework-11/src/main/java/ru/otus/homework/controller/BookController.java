package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    public BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/")
    public String listPage() {
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        BookDto bookDto;
        if (!id.equals("0")) {
            bookDto = BookDto.toDto(bookService.getById(id));
        } else {
            bookDto = new BookDto();
            bookDto.setId("0");
        }
        model.addAttribute("bookDto", bookDto);
        List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        List<Genre> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "edit";
    }

    @PostMapping("/edit")
    public String saveBook(@ModelAttribute("bookDto") BookDto bookDto) {
        String id = bookDto.getId();
        Book book = bookService.createBookOnDto(bookDto);
        if (!id.equals("0")) {
            bookService.update(book);
        } else {
            bookService.insert(book);
        }
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") String id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/{bookId}/comments")
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
    }
}
