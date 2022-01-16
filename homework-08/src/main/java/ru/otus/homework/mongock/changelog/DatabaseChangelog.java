package ru.otus.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "mitina", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertBooks", author = "mitina")
    public void insertBooks(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("books");
        String json_1 = "{title:'book 1', " +
                "author:{name:'author 1'}, " +
                "genre:{title:'genre 2'}}";
        Document document_1 = Document.parse(json_1);
        String json_2 = "{title:'book 2', " +
                "author:{name:'author 2'}, " +
                "genre:{title:'genre 1'}}";
        Document document_2 = Document.parse(json_2);
        List<Document> documentList = List.of(document_1, document_2);
        myCollection.insertMany(documentList);
    }
}
