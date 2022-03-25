package ru.otus.homework.repository.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.relational.R_Comment;

public interface R_CommentRepository extends JpaRepository<R_Comment, Long> {
}
