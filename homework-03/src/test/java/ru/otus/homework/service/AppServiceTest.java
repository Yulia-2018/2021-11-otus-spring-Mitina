package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import ru.otus.homework.Main;

import static org.mockito.Mockito.*;

@SpringBootTest
class AppServiceTest {

    private static AppService service;

    private static MessageSource msg;

    @BeforeAll
    static void setUp() {
        ApplicationContext context = SpringApplication.run(Main.class);
        service = context.getBean(AppService.class);
        msg = context.getBean(MessageSource.class);
    }

    @Test
    void testStudent() {
        ConsoleService consoleService = mock(ConsoleService.class);

        String language = service.getLanguage();
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

        service.testStudent(msg, consoleService);

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