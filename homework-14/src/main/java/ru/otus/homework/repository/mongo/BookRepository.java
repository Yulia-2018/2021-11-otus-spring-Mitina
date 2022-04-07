package ru.otus.homework.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.mongo.Book;

public interface BookRepository extends MongoRepository<Book, String> {

}
