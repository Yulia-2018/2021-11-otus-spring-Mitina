package ru.otus.homework.service;

import org.springframework.context.MessageSource;

public interface AppService {

    String getLanguage();

    void testStudent(MessageSource msg, ConsoleService consoleService);
}
