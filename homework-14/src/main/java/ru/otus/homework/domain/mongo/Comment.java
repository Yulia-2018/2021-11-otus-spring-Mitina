package ru.otus.homework.domain.mongo;

import lombok.Data;

import java.util.UUID;

@Data
public class Comment {

    private String id;

    private String text;

    public Comment() {
    }

    public Comment(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public Comment(String text) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
    }

    public Comment(Comment comment) {
        this(comment.getId(), comment.getText());
    }
}
