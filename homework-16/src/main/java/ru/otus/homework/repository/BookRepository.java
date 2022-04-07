package ru.otus.homework.repository;

import io.micrometer.core.annotation.Timed;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Timed(value = "findBooksByAuthorName.timed")
    Optional<List<Book>> findByAuthorName(String name);
}
