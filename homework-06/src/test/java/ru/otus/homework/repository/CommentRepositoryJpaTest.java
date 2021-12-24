package ru.otus.homework.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.homework.TestData.*;

@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void insert() {
        Comment comment = new Comment("new comment", BOOK_1);
        Comment newComment = new Comment(comment);
        Comment createdComment = repositoryJpa.save(newComment);
        comment.setId(createdComment.getId());
        assertThat(createdComment).usingRecursiveComparison().isEqualTo(comment);
        List<Comment> comments = repositoryJpa.getAllForBook(BOOK_1_ID);
        assertThat(comments.size()).isEqualTo(COMMENTS_FOR_BOOK_1_COUNT + 1);
        assertThat(comments).containsExactlyElementsOf(List.of(COMMENT_1, COMMENT_2, COMMENT_3, comment));
    }

    @Test
    void update() {
        Comment comment = new Comment(COMMENT_1_ID, "updated comment", BOOK_1);
        Comment updatedComment = new Comment(comment);
        Comment actualComment = repositoryJpa.save(updatedComment);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(comment);
    }

    @Test
    void getById() {
        Optional<Comment> actualComment = repositoryJpa.getById(COMMENT_1_ID);
        assertThat(actualComment).isPresent().get().usingRecursiveComparison().isEqualTo(COMMENT_1);
    }

    @Test
    void getAllForBook() {
        List<Comment> comments = repositoryJpa.getAllForBook(BOOK_1_ID);
        assertThat(comments.size()).isEqualTo(COMMENTS_FOR_BOOK_1_COUNT);
        assertThat(comments).containsExactlyElementsOf(List.of(COMMENT_1, COMMENT_2, COMMENT_3));
    }

    @Test
    void deleteById() {
        Comment comment = em.find(Comment.class, COMMENT_1_ID);
        assertThat(comment).isNotNull();
        repositoryJpa.deleteById(COMMENT_1_ID);
        Comment deletedComment = em.find(Comment.class, COMMENT_1_ID);
        assertThat(deletedComment).isNull();
    }

    @Test
    void delete() {
        Comment comment = em.find(Comment.class, COMMENT_1_ID);
        assertThat(comment).isNotNull();
        repositoryJpa.delete(comment);
        Comment deletedComment = em.find(Comment.class, COMMENT_1_ID);
        assertThat(deletedComment).isNull();
    }
}