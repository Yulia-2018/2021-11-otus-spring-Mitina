package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.homework.Main;
import ru.otus.homework.domain.Question;

import java.util.Arrays;
import java.util.List;

class QuestionServiceTest {

    private static QuestionService service;

    private static final Question QUESTION_1 = new Question("Capital of Russia, Moscow, St. Pererburg, London");
    private static final Question QUESTION_2 = new Question("The longest river in the world, Nile, Volga, Amazon");
    private static final Question QUESTION_3 = new Question("How many chromosomes does a person have, 46, 44, 42");
    private static final Question QUESTION_4 = new Question("Where Bugatti cars are made, France, Italy, Russia");
    private static final Question QUESTION_5 = new Question("Which city is depicted on a banknote of 100 rubles");

    @BeforeAll
    static void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        service = context.getBean(QuestionService.class);
    }

    @Test
    void getAll() {
        List<Question> result = service.getAll();
        Assertions.assertEquals(5, result.size());
        Assertions.assertEquals(Arrays.asList(QUESTION_1, QUESTION_2, QUESTION_3, QUESTION_4, QUESTION_5), result);
    }
}