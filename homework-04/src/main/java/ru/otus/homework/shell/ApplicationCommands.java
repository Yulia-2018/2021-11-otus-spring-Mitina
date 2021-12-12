package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.service.AppService;

@ShellComponent
public class ApplicationCommands {

    private final AppService service;

    private String consentToTesting;

    @Autowired
    public ApplicationCommands(AppService service) {
        this.service = service;
        this.consentToTesting = "-";
    }

    @ShellMethod(value = "Testing for students", key = {"t", "test", "testStudent"})
    @ShellMethodAvailability(value = "isTestCommandAvailable")
    private void testStudent() {
        service.testStudent();
    }

    @ShellMethod(value = "Consent to testing", key = {"c", "consent"})
    public String consent(@ShellOption(defaultValue = "-") String consentToTesting) {
        this.consentToTesting = consentToTesting;
        return consentToTesting.equals("+") ? "Вы подтвердили согласие на тестирование" : "Вы не подтвердили согласие на тестирование";
    }

    private Availability isTestCommandAvailable() {
        return consentToTesting.equals("+") ? Availability.available() : Availability.unavailable("Сначала подтвердите согласие на тестирование");
    }
}
