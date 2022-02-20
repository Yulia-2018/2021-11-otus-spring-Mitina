package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.homework.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

}
