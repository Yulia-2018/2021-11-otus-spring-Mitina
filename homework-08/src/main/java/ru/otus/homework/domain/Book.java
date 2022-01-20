package ru.otus.homework.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String title;

    private Author author;

    private Genre genre;

    @DBRef
    private List<Comment> comments = new ArrayList<>();;

    public Book() {
    }

    public Book(String title, Author author, Genre genre) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title, Author author, Genre genre, List<Comment> comments) {
        this(title, author, genre);
        this.comments = comments;
    }

    public Book(String id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String id, String title, Author author, Genre genre, List<Comment> comments) {
        this(id, title, author, genre);
        this.comments = comments;
    }

    public Book(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getComments());
    }

    public void addComment(Comment comment) {
        List<Comment> comments = this.getComments();
        comments.add(comment);
        this.setComments(comments);
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
