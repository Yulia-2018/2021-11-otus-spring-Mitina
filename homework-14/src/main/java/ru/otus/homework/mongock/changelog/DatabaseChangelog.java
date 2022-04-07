package ru.otus.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework.domain.mongo.Author;
import ru.otus.homework.domain.mongo.Book;
import ru.otus.homework.domain.mongo.Comment;
import ru.otus.homework.domain.mongo.Genre;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    private Author author_1 = new Author("Author_1", "author 1");
    private Author author_2 = new Author("Author_2", "author 2");
    private Genre genre_1 = new Genre("Genre_1", "genre 1");
    private Genre genre_2 = new Genre("Genre_2", "genre 2");
    private Comment comment_1 = new Comment("Comments_1", "comments 1");
    private Comment comment_2 = new Comment("Comments_2", "comments 2");
    private Comment comment_3 = new Comment("Comments_3", "comments 3");
    private Comment comment_4 = new Comment("Comments_4", "comments 4");
    private Book book_1 = new Book("book 1", author_1, genre_2, List.of(comment_1, comment_2, comment_3));
    private Book book_2 = new Book("book 2", author_2, genre_1, List.of(comment_4));

    @ChangeSet(order = "001", id = "dropDb", author = "mitina", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "mitina")
    public void insertAuthors(MongockTemplate template) {
        template.save(author_1);
        template.save(author_2);
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "mitina")
    public void insertGenres(MongockTemplate template) {
        template.save(genre_1);
        template.save(genre_2);
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "mitina")
    public void insertBooks(MongockTemplate template) {
        template.save(book_1);
        template.save(book_2);
    }
}
