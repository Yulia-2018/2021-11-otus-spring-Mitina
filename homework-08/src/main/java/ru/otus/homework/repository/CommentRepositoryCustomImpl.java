package ru.otus.homework.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public CommentRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void insertCommentInBook(Comment comment, String bookId) {
        Query query = new Query().addCriteria(where("_id").is(bookId));
        Update update = new Update().push("comments", comment);
        mongoTemplate.updateMulti(query, update, Book.class);
    }

    @Override
    public void updateCommentInBook(Comment comment) {
        Query query = new Query().addCriteria(where("comments._id").is(comment.getId()));
        Update update = new Update().set("comments.$.text", comment.getText());
        mongoTemplate.updateMulti(query, update, Book.class);
    }

    @Override
    public void deleteCommentInBook(String id) {
        Query query = new Query().addCriteria(where("_id").is(id));
        Update update = new Update().pull("comments", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }
}
