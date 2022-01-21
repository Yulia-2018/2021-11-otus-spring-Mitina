package ru.otus.homework.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;

public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public AuthorRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void updateAuthorInBooks(Author author) {
        Query query = new Query();
        query.addCriteria(Criteria.where("author._id").is(author.getId()));
        Update update = new Update();
        update.set("author.name", author.getName());
        mongoTemplate.updateMulti(query, update, Book.class);
    }

    @Override
    public Author getOrCreate(Author author) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(author.getName()));
        boolean exists = mongoTemplate.exists(query, Author.class);
        return exists ? mongoTemplate.findOne(query, Author.class) : mongoTemplate.insert(author);
    }
}
