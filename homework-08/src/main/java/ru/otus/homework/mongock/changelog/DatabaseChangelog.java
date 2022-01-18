package ru.otus.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.BookRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "mitina", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertComments", author = "mitina")
    public void insertComments(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("comments");
        Document document_1 = Document.parse("{_id: 'Comments_1', text:'comments 1'}");
        Document document_2 = Document.parse("{_id: 'Comments_2', text:'comments 2'}");
        Document document_3 = Document.parse("{_id: 'Comments_3', text:'comments 3'}");
        Document document_4 = Document.parse("{_id: 'Comments_4', text:'comments 4'}");
        List<Document> documentList = List.of(document_1, document_2, document_3, document_4);
        myCollection.insertMany(documentList);
    }

    @ChangeSet(order = "003", id = "insertBooks", author = "mitina")
    public void insertBooks(BookRepository repository) {
        repository.deleteAll();
        Book book_1 = new Book("book 1", new Author("author 1"), new Genre("genre 2"),
                List.of(new Comment("Comments_1", "comments 1"),
                        new Comment("Comments_2", "comments 2"),
                        new Comment("Comments_3", "comments 3")));
        repository.save(book_1);
        Book book_2 = new Book("book 2", new Author("author 2"), new Genre("genre 1"),
                List.of(new Comment("Comments_4", "comments 4")));
        repository.save(book_2);
    }
}
