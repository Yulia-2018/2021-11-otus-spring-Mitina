package ru.otus.homework.repository.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.relational.R_Genre;

public interface R_GenreRepository extends JpaRepository<R_Genre, Long> {
}
