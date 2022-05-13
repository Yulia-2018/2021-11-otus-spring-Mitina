package ru.otus.project;

import ru.otus.project.domain.Role;
import ru.otus.project.domain.Task;
import ru.otus.project.domain.User;

import java.time.LocalDate;
import java.util.Set;

public class TestData {

    public static final int USERS_COUNT = 2;
    public static final Long USER_ID = 100000L;
    public static final Long ADMIN_ID = 100001L;
    public static final Long NEW_USER_ID = 100011L;
    public static final Role ROLE_FOR_USER = new Role(100002L, "ROLE_USER");
    public static final Role ROLE_1_FOR_ADMIN = new Role(100003L, "ROLE_ADMIN");
    public static final Role ROLE_2_FOR_ADMIN = new Role(100004L, "ROLE_USER");
    public static final Role NEW_ROLE = new Role(100010L, "ROLE_USER");
    public static final User USER = new User(USER_ID, "User", "password", null, Set.of(ROLE_FOR_USER));
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin", null, Set.of(ROLE_1_FOR_ADMIN, ROLE_2_FOR_ADMIN));
    public static final User NEW_USER = new User(NEW_USER_ID, "New user", "New password", "TestUserName", Set.of(NEW_ROLE));

    public static final int TASKS_COUNT_FOR_USER = 2;
    public static final int TASKS_COUNT_FOR_ADMIN = 2;

    public static final Long TASK_1_ID_FOR_USER = 100005L;
    public static final Long TASK_2_ID_FOR_USER = 100006L;
    public static final Long TASK_1_ID_FOR_ADMIN = 100007L;
    public static final Long TASK_2_ID_FOR_ADMIN = 100008L;
    public static final Long NEW_TASK_ID_FOR_ADMIN = 100009L;
    public static final Task TASK_1_FOR_USER = new Task(TASK_1_ID_FOR_USER, "cook dinner", LocalDate.of(2022, 4, 10), true, USER);
    public static final Task TASK_2_FOR_USER = new Task(TASK_2_ID_FOR_USER, "buy products", LocalDate.of(2022, 7, 25), false, USER);
    public static final Task TASK_1_FOR_ADMIN = new Task(TASK_1_ID_FOR_ADMIN, "to wash the dishes", LocalDate.of(2022, 3, 10), true, ADMIN);
    public static final Task TASK_2_FOR_ADMIN = new Task(TASK_2_ID_FOR_ADMIN, "learn first lesson portuguese language", LocalDate.of(2022, 6, 12), false, ADMIN);
    public static final Task NEW_TASK_FOR_ADMIN = new Task(NEW_TASK_ID_FOR_ADMIN, "New task", LocalDate.of(2022, 7, 18), false, ADMIN);

    private TestData() {
    }
}
