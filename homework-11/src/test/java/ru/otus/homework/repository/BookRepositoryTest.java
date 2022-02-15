package ru.otus.homework.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.homework.domain.Book;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework.TestData.AUTHOR_1;
import static ru.otus.homework.TestData.GENRE_2;

@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldSetIdOnSave() {
        Mono<Book> bookMono = bookRepository.save(new Book("New book", AUTHOR_1, GENRE_2));

        StepVerifier
                .create(bookMono)
                .assertNext(book -> assertNotNull(book.getId()))
                .expectComplete()
                .verify();
    }
}