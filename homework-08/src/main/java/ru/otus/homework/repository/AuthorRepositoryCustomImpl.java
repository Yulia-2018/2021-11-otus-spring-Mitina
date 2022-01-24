package ru.otus.homework.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework.domain.Author;

public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public AuthorRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Author getOrCreate(Author author) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(author.getName()));
        boolean exists = mongoTemplate.exists(query, Author.class);
        return exists ? mongoTemplate.findOne(query, Author.class) : mongoTemplate.insert(author);
    }
}
