package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.domain.Question;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService service;

    private static Question Question_1;
    private static Question Question_2;
    private static Question Question_3;
    private static Question Question_4;
    private static Question Question_5;

    @Value("${app.language}")
    private String language;

    @Test
    void getAll() {
        List<Question> result = service.getAll();
        Assertions.assertEquals(5, result.size());

        switch (language) {
            case "ru":
                Question_1 = new Question("Столица России: Москва, Санкт-Перербург, Лондон");
                Question_2 = new Question("Самая длинная река в мире: Нил, Волга, Амазонка");
                Question_3 = new Question("Сколько хромосом у человека: 46, 44, 42");
                Question_4 = new Question("Где производятся автомобили Bugatti: Франция, Италия, Россия");
                Question_5 = new Question("Какой город изображен на купюре номиналом 100 рублей");
                break;
            case "pt":
                Question_1 = new Question("Capital da Russia: Moscou, Sao Petersburgo, Londres");
                Question_2 = new Question("O maior rio do mundo: Nilo, Volga, Amazonas");
                Question_3 = new Question("Quantos cromossomos uma pessoa tem: 46, 44, 42");
                Question_4 = new Question("Onde os carros Bugatti sao feitos: Franca, Italia, Russia");
                Question_5 = new Question("Qual cidade e retratada em uma nota de 100 rublos");
                break;
            case "en":
                Question_1 = new Question("Capital of Russia: Moscow, St. Pererburg, London");
                Question_2 = new Question("The longest river in the world: Nile, Volga, Amazon");
                Question_3 = new Question("How many chromosomes does a person have: 46, 44, 42");
                Question_4 = new Question("Where Bugatti cars are made: France, Italy, Russia");
                Question_5 = new Question("Which city is depicted on a banknote of 100 rubles");
                break;
        }

        Assertions.assertEquals(Arrays.asList(Question_1, Question_2, Question_3, Question_4, Question_5), result);
    }
}
