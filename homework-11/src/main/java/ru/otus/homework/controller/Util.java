package ru.otus.homework.controller;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;

public class Util {

    private Util() {
    }

    public static Mono<Book> getMonoBookForUpdate(Book book, String authorName, String genreTitle,
                                                  BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        String id = book.getId();
        return getOrCreateAuthorAndGenre(authorName, genreTitle, authorRepository, genreRepository)
                .zipWith(bookRepository.findById(id))
                .switchIfEmpty(Mono.error(new NotFoundException("Book " + id + " not exist")))
                .flatMap(tuple -> {
                    Book bookFromBase = tuple.getT2();
                    Tuple2<Author, Genre> tupleT1 = tuple.getT1();
                    Author author = tupleT1.getT1();
                    Genre genre = tupleT1.getT2();
                    book.setAuthor(author);
                    book.setGenre(genre);
                    book.setComments(bookFromBase.getComments());
                    return bookRepository.save(book);
                });
    }

    public static Mono<Book> getMonoBookForCreate(Book book, String authorName, String genreTitle,
                                                  BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        return getOrCreateAuthorAndGenre(authorName, genreTitle, authorRepository, genreRepository)
                .flatMap(tuple -> {
                    Author author = tuple.getT1();
                    Genre genre = tuple.getT2();
                    book.setAuthor(author);
                    book.setGenre(genre);
                    return bookRepository.save(book);
                });
    }

    private static Mono<Tuple2<Author, Genre>> getOrCreateAuthorAndGenre(String authorName, String genreTitle,
                                                                         AuthorRepository authorRepository, GenreRepository genreRepository) {
        return authorRepository.getByName(authorName)
                .switchIfEmpty(authorRepository.save(new Author(authorName)))
                .zipWith(genreRepository.getByTitle(genreTitle)
                        .switchIfEmpty(genreRepository.save(new Genre(genreTitle))));
    }
}
