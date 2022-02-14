package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.homework.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

    Mono<Genre> getByTitle(String title);
}
