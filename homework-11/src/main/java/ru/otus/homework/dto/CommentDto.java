package ru.otus.homework.dto;

import lombok.Getter;
import ru.otus.homework.domain.Comment;

@Getter
public class CommentDto {

    private String id;

    private String text;

    public CommentDto(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText());
    }
}
