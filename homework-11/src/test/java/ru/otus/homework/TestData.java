package ru.otus.homework;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;

public class TestData {

    public static final Author AUTHOR_1 = new Author("Author_1", "author 1");
    public static final Author AUTHOR_2 = new Author("Author_2", "author 2");
    public static final Genre GENRE_1 = new Genre("Genre_1", "genre 1");
    public static final Genre GENRE_2 = new Genre("Genre_2", "genre 2");
    public static final Comment COMMENTS_1 = new Comment("Comments_1", "comments 1");
    public static final Comment COMMENTS_2 = new Comment("Comments_2", "comments 2");
    public static final Comment COMMENTS_3 = new Comment("Comments_3", "comments 3");
    public static final Comment COMMENTS_4 = new Comment("Comments_4", "comments 4");
    public static final Book BOOK_1 = new Book("Book_1", "book 1", AUTHOR_1, GENRE_2, List.of(COMMENTS_1, COMMENTS_2, COMMENTS_3));
    public static final Book BOOK_2 = new Book("Book_2", "book 2", AUTHOR_2, GENRE_1, List.of(COMMENTS_4));

    private TestData() {
    }
}
