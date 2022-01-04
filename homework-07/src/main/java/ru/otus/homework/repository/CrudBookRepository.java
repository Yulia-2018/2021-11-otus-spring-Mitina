package ru.otus.homework.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface CrudBookRepository extends JpaRepository<Book, Long> {

    @Override
    Book save(Book book);

    @Override
    Optional<Book> findById(Long id);

    @EntityGraph(attributePaths = {"author", "genre"})
    @Override
    List<Book> findAll(Sort sort);

    @Override
    void deleteById(Long id);
}
