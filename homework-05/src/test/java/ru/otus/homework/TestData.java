package ru.otus.homework;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

public class TestData {

    public static final int BOOKS_COUNT = 2;
    public static final Author AUTHOR_1 = new Author(100000, "author 1");
    public static final Author AUTHOR_2 = new Author(100001, "author 2");
    public static final Genre GENRE_1 = new Genre(100002, "genre 1");
    public static final Genre GENRE_2 = new Genre(100003, "genre 2");
    public static final Book BOOK_1 = new Book(100004, "book 1", AUTHOR_1, GENRE_2);
    public static final Book BOOK_2 = new Book(100005, "book 2", AUTHOR_2, GENRE_1);

    private TestData() {
    }
}
