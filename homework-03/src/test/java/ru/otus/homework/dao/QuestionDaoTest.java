package ru.otus.homework.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.domain.Question;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class QuestionDaoTest {

    private static QuestionDaoImpl dao;

    private static final Question QUESTION_1 = new Question("Вопрос 1");
    private static final Question QUESTION_2 = new Question("Вопрос 2");
    private static final Question QUESTION_3 = new Question("Вопрос 3");
    private static final Question QUESTION_4 = new Question("Вопрос 4");
    private static final Question QUESTION_5 = new Question("Вопрос 5");

    @BeforeAll
    static void setUp() {
        dao = new QuestionDaoImpl("test_questions.csv");
    }

    @Test
    void getAll() {
        List<Question> result = dao.getAll();
        Assertions.assertEquals(5, result.size());
        Assertions.assertEquals(Arrays.asList(QUESTION_1, QUESTION_2, QUESTION_3, QUESTION_4, QUESTION_5), result);
    }
}