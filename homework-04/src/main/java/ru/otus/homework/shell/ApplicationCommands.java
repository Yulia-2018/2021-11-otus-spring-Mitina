package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.service.AppService;

@ShellComponent
public class ApplicationCommands {

    private final AppService service;

    @Autowired
    public ApplicationCommands(AppService service) {
        this.service = service;
    }

    @ShellMethod(value = "Test Student", key = {"t", "test", "testStudent"})
    private void testStudent() {
        service.testStudent();
    }
}
