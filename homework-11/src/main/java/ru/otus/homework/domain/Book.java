package ru.otus.homework.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String title;

    private Author author;

    private Genre genre;

    private List<Comment> comments = new ArrayList<>();

    public Book() {
    }

    public Book(String id) {
        this.id = id;
        this.title = "";
        this.author = new Author();
        this.genre = new Genre();
    }

    public Book(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String id, String title, Author author, Genre genre) {
        this(title, author, genre);
        this.id = id;
    }

    public Book(String id, String title, Author author, Genre genre, List<Comment> comments) {
        this(id, title, author, genre);
        this.comments = comments;
    }

    public Book(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getComments());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", comments=" + comments +
                '}';
    }
}
