package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.repository.mongo.AuthorRepository;
import ru.otus.homework.repository.mongo.BookRepository;
import ru.otus.homework.repository.mongo.GenreRepository;
import ru.otus.homework.repository.relational.R_AuthorRepository;
import ru.otus.homework.repository.relational.R_BookRepository;
import ru.otus.homework.repository.relational.R_GenreRepository;

@ShellComponent
public class ApplicationCommands {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    private final R_AuthorRepository r_authorRepository;
    private final R_GenreRepository r_genreRepository;
    private final R_BookRepository r_bookRepository;

    public ApplicationCommands(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository, R_AuthorRepository r_authorRepository, R_GenreRepository r_genreRepository, R_BookRepository r_bookRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.r_authorRepository = r_authorRepository;
        this.r_genreRepository = r_genreRepository;
        this.r_bookRepository = r_bookRepository;
    }

    @ShellMethod(value = "Get all authors (relational)", key = "allAr")
    private void getAllRAuthors() {
        r_authorRepository.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get all genres (relational)", key = "allGr")
    private void getAllRGenres() {
        r_genreRepository.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get all books (relational)", key = "allBr")
    private void getAllRBooks() {
        r_bookRepository.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get all authors (mongo)", key = "allAm")
    private void getAllAuthors() {
        authorRepository.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get all genres (mongo)", key = "allGm")
    private void getAllGenres() {
        genreRepository.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get all books (mongo)", key = "allBm")
    private void getAllBooks() {
        bookRepository.findAll().forEach(System.out::println);
    }

    /*@ShellMethod(value = "Get all comments for book", key = {"allC", "allComments", "getAllComments"})
    private void getAllComments(@ShellOption String bookId) {
        bookService.getById(bookId).getComments().forEach(System.out::println);
    }*/
}
