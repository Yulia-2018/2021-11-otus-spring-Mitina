package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {

    private MessageSource messageSource;

    private Locale locale;

    public MessageService(MessageSource messageSource, @Value("${app.language}") String language) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(language.equals("en") ? "" : language);
    }

    public String getMessage(String code, String[] args) {
        return messageSource.getMessage(code, args, locale);
    }
}
