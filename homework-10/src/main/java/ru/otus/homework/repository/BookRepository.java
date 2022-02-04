package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    @Override
    List<Book> findAll();
}
