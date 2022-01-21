package ru.otus.homework.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public GenreRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void updateGenreInBooks(Genre genre) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genre._id").is(genre.getId()));
        Update update = new Update();
        update.set("genre.title", genre.getTitle());
        mongoTemplate.updateMulti(query, update, Book.class);
    }

    @Override
    public Genre getOrCreate(Genre genre) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(genre.getTitle()));
        boolean exists = mongoTemplate.exists(query, Genre.class);
        return exists ? mongoTemplate.findOne(query, Genre.class) : mongoTemplate.insert(genre);
    }
}
