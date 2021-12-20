package ru.otus.homework.domain;

import lombok.Data;

@Data
public class Book {

    private long id;

    private final String title;

    private Author author;

    private Genre genre;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(long id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre());
    }
}
