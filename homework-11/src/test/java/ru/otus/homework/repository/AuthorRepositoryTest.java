package ru.otus.homework.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.homework.domain.Author;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void shouldSetIdOnSave() {
        Mono<Author> authorMono = authorRepository.save(new Author("New author"));

        StepVerifier
                .create(authorMono)
                .assertNext(author -> assertNotNull(author.getId()))
                .expectComplete()
                .verify();
    }
}