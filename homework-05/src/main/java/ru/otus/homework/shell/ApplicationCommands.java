package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.BookService;

@ShellComponent
public class ApplicationCommands {

    private final BookService service;

    public ApplicationCommands(BookService service) {
        this.service = service;
    }

    @ShellMethod(value = "Get all books", key = {"all", "getAll"})
    private void getAllBooks() {
        service.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get book by id", key = {"g", "get"})
    private void getBookById(@ShellOption Long id) {
        System.out.println(service.getById(id));
    }

    @ShellMethod(value = "Delete book by id", key = {"d", "delete"})
    private void deleteBookById(@ShellOption Long id) {
        service.deleteById(id);
        System.out.println("Book " + id + " deleted");
    }

    @ShellMethod(value = "Insert book", key = {"i", "insert"})
    private void insertBook(@ShellOption String titleBook, @ShellOption String nameAuthor, @ShellOption String titleGenre) {
        Book book = new Book(titleBook, new Author(nameAuthor), new Genre(titleGenre));
        System.out.println(service.insert(book));
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
    private void updateBook(@ShellOption Long idBook, @ShellOption String titleBook, @ShellOption String nameAuthor, @ShellOption String titleGenre) {
        Book book = new Book(idBook, titleBook, new Author(nameAuthor), new Genre(titleGenre));
        service.update(book);
        System.out.println("Book " + idBook + " updated");
    }
}
