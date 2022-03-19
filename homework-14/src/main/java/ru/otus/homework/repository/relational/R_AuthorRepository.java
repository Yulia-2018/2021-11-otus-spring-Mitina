package ru.otus.homework.repository.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.relational.R_Author;

public interface R_AuthorRepository extends JpaRepository<R_Author, Long> {
}
