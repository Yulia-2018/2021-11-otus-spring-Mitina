package ru.otus.homework.repository;

import ru.otus.homework.domain.Comment;

public interface CommentRepositoryCustom {

    void insertCommentInBook(Comment comment, String bookId);

    void updateCommentInBook(Comment comment);

    void deleteCommentInBook(String id);
}
