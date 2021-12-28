package ru.otus.homework;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

public class TestData {

    public static final int BOOKS_COUNT = 2;
    public static final Author AUTHOR_1 = new Author(100000, "author 1");
    public static final Author AUTHOR_2 = new Author(100001, "author 2");
    public static final Genre GENRE_1 = new Genre(100002, "genre 1");
    public static final Genre GENRE_2 = new Genre(100003, "genre 2");
    public static final long BOOK_1_ID = 100004;
    public static final long BOOK_2_ID = 100005;
    public static final Book BOOK_1 = new Book(BOOK_1_ID, "book 1", AUTHOR_1, GENRE_2);
    public static final Book BOOK_2 = new Book(BOOK_2_ID, "book 2", AUTHOR_2, GENRE_1);
    public static final long COMMENT_1_ID = 100006;
    public static final Comment COMMENT_1 = new Comment(COMMENT_1_ID, "comment 1", BOOK_1);
    public static final Comment COMMENT_2 = new Comment(COMMENT_1_ID + 1, "comment 2", BOOK_1);
    public static final Comment COMMENT_3 = new Comment(COMMENT_1_ID + 2, "comment 3", BOOK_1);
    public static final Comment COMMENT_4 = new Comment(COMMENT_1_ID + 3, "comment 4", BOOK_2);
    public static final long COMMENTS_FOR_BOOK_1_COUNT = 3;

    private TestData() {
    }
}
