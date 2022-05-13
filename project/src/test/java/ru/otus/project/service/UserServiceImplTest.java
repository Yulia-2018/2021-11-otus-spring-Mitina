package ru.otus.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.project.domain.User;
import ru.otus.project.exception.NotFoundException;
import ru.otus.project.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;
import static ru.otus.project.TestData.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(List.of(USER, ADMIN));

        List<User> users = service.getAll();
        assertThat(users.size()).isEqualTo(USERS_COUNT);
        assertThat(users).containsExactlyElementsOf(List.of(USER, ADMIN));

        verify(repository, times(1)).findAll();
    }

    @Test
    void getById() {
        when(repository.findById(USER_ID)).thenReturn(Optional.of(USER));

        User actualUser = service.getById(USER_ID);
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(USER);

        verify(repository, times(1)).findById(USER_ID);
    }

    @Test
    void getByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatCode(() -> service.getById(1L)).isInstanceOf(NotFoundException.class).hasMessage("User " + 1 + " not exist");

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void insert() {
        User newUser = new User(NEW_USER.getName(), NEW_USER.getPassword(), NEW_USER.getTelegramUsername(), NEW_USER.getRoles());

        when(repository.save(newUser)).thenReturn(NEW_USER);
        when(repository.findAll()).thenReturn(List.of(USER, ADMIN, NEW_USER));

        User createdUser = service.insert(newUser);
        newUser.setId(createdUser.getId());
        assertThat(createdUser).usingRecursiveComparison().isEqualTo(newUser);
        List<User> users = service.getAll();
        assertThat(users.size()).isEqualTo(USERS_COUNT + 1);
        assertThat(users).containsExactlyElementsOf(List.of(USER, ADMIN, newUser));

        verify(repository, times(1)).save(newUser);
        verify(repository, times(1)).findAll();
    }
}