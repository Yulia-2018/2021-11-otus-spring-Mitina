package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Genre;

import java.util.Optional;

public interface CrudGenreRepository extends JpaRepository<Genre, Long> {

    @Override
    Genre save(Genre genre);

    @Override
    Optional<Genre> findById(Long id);

    Optional<Genre> getByTitle(String title);
}
