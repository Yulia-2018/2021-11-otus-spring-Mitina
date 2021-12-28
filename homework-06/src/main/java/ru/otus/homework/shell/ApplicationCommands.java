package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;

@ShellComponent
public class ApplicationCommands {

    private final BookService bookService;

    private final CommentService commentService;

    public ApplicationCommands(BookService service, CommentService commentService) {
        this.bookService = service;
        this.commentService = commentService;
    }

    @ShellMethod(value = "Get all books", key = {"all", "getAll"})
    private void getAllBooks() {
        bookService.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get book by id", key = {"g", "get"})
    private void getBookById(@ShellOption Long id) {
        System.out.println(bookService.getById(id));
    }

    @ShellMethod(value = "Get book by id with comments", key = {"gW", "getWithComments"})
    private void getBookByIdWithComments(@ShellOption Long id) {
        Book bookWithComments = bookService.getByIdWithComments(id);
        System.out.println(bookWithComments);
        bookWithComments.getComments().forEach(System.out::println);
    }

    @ShellMethod(value = "Delete book by id", key = {"d", "delete"})
    private void deleteBookById(@ShellOption Long id) {
        bookService.deleteById(id);
        System.out.println("Book " + id + " deleted");
    }

    @ShellMethod(value = "Insert book", key = {"i", "insert"})
    private void insertBook(@ShellOption String titleBook, @ShellOption String nameAuthor, @ShellOption String titleGenre) {
        Book book = new Book(titleBook, new Author(nameAuthor), new Genre(titleGenre));
        System.out.println(bookService.insert(book));
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
    private void updateBook(@ShellOption Long idBook, @ShellOption String titleBook, @ShellOption String nameAuthor, @ShellOption String titleGenre) {
        Book book = new Book(idBook, titleBook, new Author(nameAuthor), new Genre(titleGenre));
        bookService.update(book);
        System.out.println("Book " + idBook + " updated");
    }

    @ShellMethod(value = "Get all comments for book", key = {"allC", "allComments", "getAllComments"})
    private void getAllComments(@ShellOption Long idBook) {
        commentService.getAllForBook(idBook).forEach(System.out::println);
    }

    @ShellMethod(value = "Get comment by id", key = {"gC", "getComment"})
    private void getCommentById(@ShellOption Long id, @ShellOption Long idBook) {
        System.out.println(commentService.getById(id, idBook));
    }

    @ShellMethod(value = "Delete comment for book by id", key = {"dC", "deleteComment"})
    private void deleteCommentById(@ShellOption Long id, @ShellOption Long idBook) {
        commentService.deleteById(id, idBook);
        System.out.println("Comment " + id + " for book " + idBook + " deleted");
    }

    @ShellMethod(value = "Insert comment for book", key = {"iC", "insertComment"})
    private void insertCommentForBook(@ShellOption String text, @ShellOption Long idBook) {
        Book book = new Book(idBook, "", null, null);
        Comment comment = new Comment(text, book);
        System.out.println(commentService.insert(comment, idBook));
    }

    @ShellMethod(value = "Update comment for book", key = {"uC", "updateComment"})
    private void updateCommentForBook(@ShellOption Long id, @ShellOption String text, @ShellOption Long idBook) {
        Book book = new Book(idBook, "", null, null);
        Comment comment = new Comment(id, text, book);
        commentService.update(comment, idBook);
        System.out.println("Comment " + id + " for book " + idBook + " updated");
    }
}
