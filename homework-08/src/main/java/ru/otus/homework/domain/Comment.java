package ru.otus.homework.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    private String text;

    public Comment() {
    }

    public Comment(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public Comment(String text) {
        this.text = text;
    }

    public Comment(Comment comment) {
        this(comment.getId(), comment.getText());
    }
}
