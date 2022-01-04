package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Author;

import java.util.Optional;

public interface CrudAuthorRepository extends JpaRepository<Author, Long> {

    @Override
    Author save(Author author);

    @Override
    Optional<Author> findById(Long id);

    Optional<Author> getByName(String name);
}
