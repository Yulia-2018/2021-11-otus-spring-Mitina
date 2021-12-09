package ru.otus.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
class AppServiceTest {

    @Autowired
    private AppServiceImpl service;

    @MockBean
    private ConsoleService consoleService;

    @Value("${app.language}")
    private String language;

    @Test
    void testStudent() {

        if (language.equals("ru")) {
            when(consoleService.read()).thenReturn(
                    "Иванов", "Михаил",
                    "Москва", "Нил", "4 6", "Франция", "Москва");
        } else if (language.equals("pt")) {
            when(consoleService.read()).thenReturn(
                    "Rego", "Fernanda",
                    "Moscou", "Nilo", "4 6", "Franca", "Moscou");
        } else {
            when(consoleService.read()).thenReturn(
                    "Williams", "Alex",
                    "Moscow", "Nile", "4 6", "France", "Moscow");
        }

        service.testStudent();

        verify(consoleService, times(7)).read();
        verify(consoleService, times(8)).write(any());
        if (language.equals("ru")) {
            verify(consoleService, times(1)).write("Михаил Иванов, результат вашего тестирования: 4 из 5 вопросов");
        } else if (language.equals("pt")) {
            verify(consoleService, times(1)).write("Fernanda Rego, resultado do seu teste: 4 de 5 perguntas");
        } else {
            verify(consoleService, times(1)).write("Alex Williams, the result of your testing: 4 of 5 questions");
        }
    }
}