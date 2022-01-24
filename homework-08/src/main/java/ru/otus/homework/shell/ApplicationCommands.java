package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.Iterator;
import java.util.List;

@ShellComponent
public class ApplicationCommands {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    public ApplicationCommands(BookService service, AuthorService authorService, GenreService genreService) {
        this.bookService = service;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @ShellMethod(value = "Get all books", key = {"all", "getAll"})
    private void getAllBooks() {
        bookService.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get book by id", key = {"g", "get"})
    private void getBookById(@ShellOption String id) {
        System.out.println(bookService.getById(id));
    }

    @ShellMethod(value = "Delete book by id", key = {"d", "delete"})
    private void deleteBookById(@ShellOption String id) {
        bookService.deleteById(id);
        System.out.println("Book " + id + " deleted");
    }

    @ShellMethod(value = "Insert book", key = {"i", "insert"})
    private void insertBook(@ShellOption String titleBook, @ShellOption String nameAuthor, @ShellOption String titleGenre) {
        Book book = new Book(titleBook, new Author(nameAuthor), new Genre(titleGenre));
        System.out.println(bookService.insert(book));
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
    private void updateBook(@ShellOption String id, @ShellOption String titleBook, @ShellOption String nameAuthor, @ShellOption String titleGenre) {
        Book book = new Book(id, titleBook, new Author(nameAuthor), new Genre(titleGenre));
        bookService.update(book);
        System.out.println("Book " + id + " updated");
    }

    @ShellMethod(value = "Get all genres", key = {"allG", "getAllGenres"})
    private void getAllGenres() {
        genreService.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get genre by id", key = {"gG", "getGenre"})
    private void getGenreById(@ShellOption String id) {
        System.out.println(genreService.getById(id));
    }

    @ShellMethod(value = "Update genre", key = {"uG", "updateGenre"})
    private void updateGenre(@ShellOption String id, @ShellOption String title) {
        Genre genre = new Genre(id, title);
        genreService.update(genre);
        System.out.println("Genre " + id + " updated");
    }

    @ShellMethod(value = "Get all authors", key = {"allA", "getAllAuthors"})
    private void getAllAuthors() {
        authorService.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get author by id", key = {"gA", "getAuthor"})
    private void getAuthorById(@ShellOption String id) {
        System.out.println(authorService.getById(id));
    }

    @ShellMethod(value = "Update author", key = {"uA", "updateAuthor"})
    private void updateAuthor(@ShellOption String id, @ShellOption String name) {
        Author author = new Author(id, name);
        authorService.update(author);
        System.out.println("Author " + id + " updated");
    }

    @ShellMethod(value = "Get all comments for book", key = {"allC", "allComments", "getAllComments"})
    private void getAllComments(@ShellOption String bookId) {
        bookService.getById(bookId).getComments().forEach(System.out::println);
    }

    @ShellMethod(value = "Get comment by id", key = {"gC", "getComment"})
    private void getCommentById(@ShellOption String id) {
        List<Book> books = bookService.getAll();
        books.forEach((book -> {
            List<Comment> comments = book.getComments();
            for (Comment comment : comments) {
                if (comment.getId().equals(id)) {
                    System.out.println(comment);
                    break;
                }
            }
        }));
    }

    @ShellMethod(value = "Delete comment for book by id", key = {"dC", "deleteComment"})
    private void deleteCommentById(@ShellOption String id, @ShellOption String bookId) {
        Book book = bookService.getById(bookId);
        List<Comment> comments = book.getComments();
        Iterator<Comment> iterator = comments.iterator();
        while (iterator.hasNext()) {
            Comment comment = iterator.next();
            if (comment.getId().equals(id)) {
                iterator.remove();
                bookService.update(book);
                System.out.println("Comment " + id + " deleted");
                break;
            }
        }
    }

    @ShellMethod(value = "Insert comment for book", key = {"iC", "insertComment"})
    private void insertCommentForBook(@ShellOption String text, @ShellOption String bookId) {
        Comment comment = new Comment(text);
        Book book = bookService.getById(bookId);
        List<Comment> comments = book.getComments();
        comments.add(comment);
        bookService.update(book);
        System.out.println("Comment " + comment.getId() + " inserted");
    }

    @ShellMethod(value = "Update comment for book", key = {"uC", "updateComment"})
    private void updateCommentForBook(@ShellOption String id, @ShellOption String text, @ShellOption String bookId) {
        Book book = bookService.getById(bookId);
        List<Comment> comments = book.getComments();
        for (Comment comment : comments) {
            if (comment.getId().equals(id)) {
                comment.setText(text);
                bookService.update(book);
                System.out.println("Comment " + id + " updated");
                break;
            }
        }
    }
}
