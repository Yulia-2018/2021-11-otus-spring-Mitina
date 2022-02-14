package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;

@EnableMongoRepositories
@SpringBootApplication
public class Main {

    private static final Author AUTHOR_1 = new Author("Author_1", "author 1");
    private static final Author AUTHOR_2 = new Author("Author_2", "author 2");
    private static final Genre GENRE_1 = new Genre("Genre_1", "genre 1");
    private static final Genre GENRE_2 = new Genre("Genre_2", "genre 2");
    private static final Comment COMMENTS_1 = new Comment("Comments_1", "comments 1");
    private static final Comment COMMENTS_2 = new Comment("Comments_2", "comments 2");
    private static final Comment COMMENTS_3 = new Comment("Comments_3", "comments 3");
    private static final Comment COMMENTS_4 = new Comment("Comments_4", "comments 4");
    private static final Book BOOK_1 = new Book("Book_1", "book 1", AUTHOR_1, GENRE_2, List.of(COMMENTS_1, COMMENTS_2, COMMENTS_3));
    private static final Book BOOK_2 = new Book("Book_2", "book 2", AUTHOR_2, GENRE_1, List.of(COMMENTS_4));

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        GenreRepository genreRepository = context.getBean(GenreRepository.class);
        BookRepository bookRepository = context.getBean(BookRepository.class);

        authorRepository.deleteAll().subscribe();
        authorRepository.saveAll(Arrays.asList(AUTHOR_1, AUTHOR_2)).subscribe();

        genreRepository.deleteAll().subscribe();
        genreRepository.saveAll(Arrays.asList(GENRE_1, GENRE_2)).subscribe();

        bookRepository.deleteAll().subscribe();
        bookRepository.saveAll(Arrays.asList(BOOK_1, BOOK_2)).subscribe();
    }
}
