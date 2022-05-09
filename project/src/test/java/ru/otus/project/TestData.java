package ru.otus.project;

import ru.otus.project.domain.Task;

import java.time.LocalDate;

public class TestData {

    public static final int USERS_COUNT = 2;
    public static final Long USER_ID = 100000L;
    public static final Long ADMIN_ID = 100001L;
    public static final int TASKS_COUNT_FOR_ADMIN = 2;
    public static final Long TASK_1_ID_FOR_USER = 100005L;
    public static final Long TASK_2_ID_FOR_USER = 100006L;
    public static final Long TASK_1_ID_FOR_ADMIN = 100007L;
    public static final Long TASK_2_ID_FOR_ADMIN = 100008L;
    public static final Task TASK_1_FOR_USER = new Task(TASK_1_ID_FOR_USER, "cook dinner", LocalDate.of(2022, 4, 10), true);
    public static final Task TASK_2_FOR_USER = new Task(TASK_2_ID_FOR_USER, "buy products", LocalDate.of(2022, 7, 25), false);
    public static final Task TASK_1_FOR_ADMIN = new Task(TASK_1_ID_FOR_ADMIN, "to wash the dishes", LocalDate.of(2022, 3, 10), true);
    public static final Task TASK_2_FOR_ADMIN = new Task(TASK_2_ID_FOR_ADMIN, "learn first lesson portuguese language", LocalDate.of(2022, 6, 12), false);

    private TestData() {
    }
}
