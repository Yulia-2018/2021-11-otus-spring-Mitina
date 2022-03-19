package ru.otus.homework.repository.relational;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.relational.R_Book;

import java.util.List;

public interface R_BookRepository extends JpaRepository<R_Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    @Override
    List<R_Book> findAll();
}
