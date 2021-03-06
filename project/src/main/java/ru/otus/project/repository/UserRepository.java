package ru.otus.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByName(String name);

    Optional<User> getByTelegramUsername(String telegramUsername);

    List<User> getByTelegramChatIdIsNotNull();
}
