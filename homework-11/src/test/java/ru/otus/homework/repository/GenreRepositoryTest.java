package ru.otus.homework.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.homework.domain.Genre;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void shouldSetIdOnSave() {
        Mono<Genre> genreMono = genreRepository.save(new Genre("New genre"));

        StepVerifier
                .create(genreMono)
                .assertNext(genre -> assertNotNull(genre.getId()))
                .expectComplete()
                .verify();
    }
}