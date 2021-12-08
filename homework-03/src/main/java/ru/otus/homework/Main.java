package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework.service.AppService;
import ru.otus.homework.service.ConsoleService;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        AppService appService = context.getBean(AppService.class);
        ConsoleService consoleService = context.getBean(ConsoleService.class);
        appService.testStudent(consoleService);
    }
}
