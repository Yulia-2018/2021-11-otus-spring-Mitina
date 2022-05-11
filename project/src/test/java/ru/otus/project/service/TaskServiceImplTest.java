package ru.otus.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.project.domain.Task;
import ru.otus.project.exception.NotFoundException;
import ru.otus.project.repository.TaskRepository;
import ru.otus.project.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;
import static ru.otus.project.TestData.*;

@SpringBootTest
class TaskServiceImplTest {

    @Autowired
    private TaskServiceImpl service;

    @MockBean
    private TaskRepository repository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(List.of(TASK_1_FOR_USER, TASK_2_FOR_USER, TASK_1_FOR_ADMIN, TASK_2_FOR_ADMIN));

        List<Task> tasks = service.getAll(ADMIN_ID);
        assertThat(tasks.size()).isEqualTo(TASKS_COUNT_FOR_ADMIN);
        assertThat(tasks).containsExactlyElementsOf(List.of(TASK_2_FOR_ADMIN, TASK_1_FOR_ADMIN));

        verify(repository, times(1)).findAll();
    }

    @Test
    void getById() {
        when(repository.findById(TASK_2_ID_FOR_USER)).thenReturn(Optional.of(TASK_2_FOR_USER));

        Task actualTask = service.getById(TASK_2_ID_FOR_USER, USER_ID);
        assertThat(actualTask).usingRecursiveComparison().isEqualTo(TASK_2_FOR_USER);

        verify(repository, times(1)).findById(TASK_2_ID_FOR_USER);
    }

    @Test
    void getByIdAnotherData() {
        when(repository.findById(TASK_2_ID_FOR_ADMIN)).thenReturn(Optional.of(TASK_2_FOR_ADMIN));

        assertThatCode(() -> service.getById(TASK_2_ID_FOR_ADMIN, USER_ID)).isInstanceOf(NotFoundException.class).hasMessage("Task " + TASK_2_ID_FOR_ADMIN + " not exist");

        verify(repository, times(1)).findById(TASK_2_ID_FOR_ADMIN);
    }

    @Test
    void getByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatCode(() -> service.getById(1L, USER_ID)).isInstanceOf(NotFoundException.class).hasMessage("Task " + 1 + " not exist");

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void insert() {
        Task newTask = new Task(NEW_TASK_FOR_ADMIN.getDescription(), NEW_TASK_FOR_ADMIN.getDeadline(), NEW_TASK_FOR_ADMIN.getDone(), NEW_TASK_FOR_ADMIN.getUser());

        when(userRepository.findById(ADMIN_ID)).thenReturn(Optional.of(ADMIN));
        when(repository.save(newTask)).thenReturn(NEW_TASK_FOR_ADMIN);
        when(repository.findAll()).thenReturn(List.of(TASK_1_FOR_USER, TASK_2_FOR_USER, TASK_1_FOR_ADMIN, TASK_2_FOR_ADMIN, NEW_TASK_FOR_ADMIN));

        Task createdTask = service.insert(newTask, ADMIN_ID);
        newTask.setId(createdTask.getId());
        assertThat(createdTask).usingRecursiveComparison().ignoringFields("user.name", "user.password", "user.roles", "user.tasks").isEqualTo(newTask);
        List<Task> tasks = service.getAll(ADMIN_ID);
        assertThat(tasks.size()).isEqualTo(TASKS_COUNT_FOR_ADMIN + 1);
        assertThat(tasks).usingRecursiveFieldByFieldElementComparatorIgnoringFields("user.name", "user.password", "user.roles", "user.tasks").containsExactlyElementsOf(List.of(TASK_2_FOR_ADMIN, newTask, TASK_1_FOR_ADMIN));

        verify(repository, times(1)).save(newTask);
        verify(repository, times(1)).findAll();
        verify(userRepository, times(1)).findById(ADMIN_ID);
    }

    @Test
    void update() {
        Task expectedTask = new Task(TASK_1_ID_FOR_USER, "Updated task", LocalDate.of(2022, 12, 5), false, USER);
        Task updatedTask = new Task(expectedTask);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        when(repository.findById(TASK_1_ID_FOR_USER)).thenReturn(Optional.of(TASK_1_FOR_USER));
        when(repository.save(updatedTask)).thenReturn(expectedTask);

        service.update(updatedTask, USER_ID);

        when(repository.findById(TASK_1_ID_FOR_USER)).thenReturn(Optional.of(expectedTask));
        Task actualTask = service.getById(TASK_1_ID_FOR_USER, USER_ID);
        assertThat(actualTask).usingRecursiveComparison().isEqualTo(expectedTask);

        verify(repository, times(1)).save(updatedTask);
        verify(repository, times(2)).findById(TASK_1_ID_FOR_USER);
        verify(userRepository, times(1)).findById(USER_ID);
    }

    @Test
    void updateAnotherData() {
        Task expectedTask = new Task(TASK_1_ID_FOR_USER, "Updated task", LocalDate.of(2022, 12, 5), false, USER);
        Task updatedTask = new Task(expectedTask);

        when(repository.findById(TASK_1_ID_FOR_USER)).thenReturn(Optional.of(TASK_1_FOR_USER));

        assertThatCode(() -> service.update(updatedTask, ADMIN_ID)).isInstanceOf(NotFoundException.class).hasMessage("Task " + TASK_1_ID_FOR_USER + " not exist");

        verify(repository, times(1)).findById(TASK_1_ID_FOR_USER);
        verify(repository, times(0)).save(any());
    }

    @Test
    void updateNotFound() {
        Task updatedTask = new Task(1L, "Updated task", LocalDate.of(2022, 12, 5), false);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatCode(() -> service.update(updatedTask, ADMIN_ID)).isInstanceOf(NotFoundException.class).hasMessage("Task " + 1 + " not exist");

        verify(repository, times(1)).findById(1L);
        verify(repository, times(0)).save(any());
    }

    @Test
    void deleteById() {
        when(repository.findById(TASK_1_ID_FOR_USER)).thenReturn(Optional.of(TASK_1_FOR_USER));
        when(repository.findAll()).thenReturn(List.of(TASK_2_FOR_USER, TASK_1_FOR_ADMIN, TASK_2_FOR_ADMIN));

        service.deleteById(TASK_1_ID_FOR_USER, USER_ID);
        List<Task> tasks = service.getAll(USER_ID);
        assertThat(tasks.size()).isEqualTo(TASKS_COUNT_FOR_USER - 1);
        assertThat(tasks).containsExactlyElementsOf(List.of(TASK_2_FOR_USER));

        verify(repository, times(1)).findById(TASK_1_ID_FOR_USER);
        verify(repository, times(1)).delete(TASK_1_FOR_USER);
        verify(repository, times(1)).findAll();
    }

    @Test
    void deleteByIdAnotherData() {
        when(repository.findById(TASK_1_ID_FOR_USER)).thenReturn(Optional.of(TASK_1_FOR_USER));

        assertThatCode(() -> service.deleteById(TASK_1_ID_FOR_USER, ADMIN_ID)).isInstanceOf(NotFoundException.class).hasMessage("Task " + TASK_1_ID_FOR_USER + " not exist");

        verify(repository, times(1)).findById(TASK_1_ID_FOR_USER);
        verify(repository, times(0)).delete(TASK_1_FOR_USER);
    }

    @Test
    void deleteByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatCode(() -> service.deleteById(1L, ADMIN_ID)).isInstanceOf(NotFoundException.class).hasMessage("Task " + 1 + " not exist");

        verify(repository, times(1)).findById(1L);
        verify(repository, times(0)).delete(any());
    }
}