package ru.otus.homework.domain;

import lombok.Data;

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
        this.text = text;
    }

    public Comment(Comment comment) {
        this(comment.getId(), comment.getText());
    }
}
