package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

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
    public String editPage(@RequestParam("id") int id, Model model) {
        BookDto bookDto;
        if (id != 0) {
            bookDto = BookDto.toDto(bookService.getById(id));
        } else {
            bookDto = new BookDto();
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
        long id = bookDto.getId();
        Book book = bookService.createBookOnDto(bookDto);
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
