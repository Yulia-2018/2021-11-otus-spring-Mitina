package ru.otus.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.domain.Book;
import ru.otus.homework.repository.BookRepository;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static ru.otus.homework.TestData.*;

@SpringBootTest
class BookRestControllerTest {

    @MockBean
    private BookRepository bookRepository;

    @Bean
    RouterFunction<ServerResponse> getAllBookRoute() {
        return route(GET("/books"),
                req -> ok().body(bookRepository.findAll(), Book.class));
    }

    @Bean
    RouterFunction<ServerResponse> getBookByIdRoute() {
        return route(GET("/books/{id}"),
                req -> ok().body(bookRepository.findById(req.pathVariable("id")), Book.class));
    }

    @Bean
    RouterFunction<ServerResponse> deleteBookByIdRoute() {
        return route(DELETE("/books/{id}"),
                req -> ok().body(bookRepository.deleteById(req.pathVariable("id")), Book.class));
    }

    @Bean
    RouterFunction<ServerResponse> updateBookRoute() {
        return route(PUT("/books/{id}"),
                req -> req.body(toMono(Book.class))
                        .doOnNext(bookRepository::save)
                        .then(ok().build()));
    }

    @Bean
    RouterFunction<ServerResponse> createBookRoute() {
        return route(POST("/books"),
                req -> req.body(toMono(Book.class))
                        .doOnNext(bookRepository::save)
                        .then(ok().build()));
    }

    @Test
    void getAll() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(getAllBookRoute())
                .build();

        List<Book> books = List.of(BOOK_1, BOOK_2);
        Flux<Book> bookFlux = Flux.fromIterable(books);
        given(bookRepository.findAll()).willReturn(bookFlux);

        client.get()
                .uri("/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class)
                .isEqualTo(books);
    }

    @Test
    void get() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(getBookByIdRoute())
                .build();

        Book book = BOOK_1;
        given(bookRepository.findById(BOOK_1.getId())).willReturn(Mono.just(book));

        client.get()
                .uri("/books/" + BOOK_1.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class)
                .isEqualTo(book);
    }

    @Test
    void delete() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(deleteBookByIdRoute())
                .build();

        given(bookRepository.deleteById(BOOK_1.getId())).willReturn(Mono.empty());

        client.delete()
                .uri("/books/" + BOOK_1.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class)
                .isEqualTo(null);
    }

    @Test
    void update() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(updateBookRoute())
                .build();

        Book updatedBook = new Book(BOOK_1.getId(),"Updated Book", AUTHOR_2, GENRE_2);

        client.put()
                .uri("/books/" + BOOK_1.getId())
                .body(Mono.just(updatedBook), Book.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void create() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(createBookRoute())
                .build();

        Book newBook = new Book(UUID.randomUUID().toString(),"New Book", AUTHOR_1, GENRE_2);

        client.post()
                .uri("/books")
                .body(Mono.just(newBook), Book.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}