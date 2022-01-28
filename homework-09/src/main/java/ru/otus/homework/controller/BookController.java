package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    public BookController(BookService service, AuthorService authorService, GenreService genreService) {
        this.bookService = service;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") int id, Model model) {
        Book book;
        if (id != 0) {
            book = bookService.getById(id);
        } else {
            book = new Book();
        }
        model.addAttribute("book", book);
        List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        List<Genre> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "edit";
    }

    @PostMapping("/edit")
    public String saveBook(@RequestParam("id") int id, @RequestParam("title") String title,
                           @RequestParam("author") int authorId, @RequestParam("genre") int genreId) {
        Book book;
        if (id != 0) {
            book = bookService.getById(id);
        } else {
            book = new Book();
        }

        book.setTitle(title);
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        book.setAuthor(author);
        book.setGenre(genre);

        if (id != 0) {
            bookService.update(book);
        } else {
            bookService.insert(book);
        }
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") int id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
