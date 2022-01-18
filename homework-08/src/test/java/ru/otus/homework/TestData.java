package ru.otus.homework;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;

public class TestData {

    public static final String COMMENT_1_ID = "Comments_1";
    public static final Comment COMMENT_1 = new Comment(COMMENT_1_ID, "comment 1");
    public static final Comment COMMENT_2 = new Comment("Comments_2", "comment 2");
    public static final Comment COMMENT_3 = new Comment("Comments_3", "comment 3");
    public static final Comment COMMENT_4 = new Comment("Comments_4", "comment 4");
    public static final int COMMENTS_FOR_BOOK_1_COUNT = 3;

    public static final int BOOKS_COUNT = 2;
    public static final Author AUTHOR_1 = new Author("author 1");
    public static final Author AUTHOR_2 = new Author("author 2");
    public static final Genre GENRE_1 = new Genre("genre 1");
    public static final Genre GENRE_2 = new Genre("genre 2");
    public static final String BOOK_1_ID = "100000";
    public static final String BOOK_2_ID = "100001";
    public static final Book BOOK_1 = new Book(BOOK_1_ID, "book 1", AUTHOR_1, GENRE_2, List.of(COMMENT_1, COMMENT_2, COMMENT_3));
    public static final Book BOOK_2 = new Book(BOOK_2_ID, "book 2", AUTHOR_2, GENRE_1, List.of(COMMENT_4));

    private TestData() {
    }
}
