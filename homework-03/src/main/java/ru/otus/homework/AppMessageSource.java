package ru.otus.homework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class AppMessageSource {

    private MessageSource messageSource;

    private Locale locale;

    public AppMessageSource(MessageSource messageSource, @Value("${app.language}") String language) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(language.equals("en") ? "" : language);
    }

    public String getMessage(String code, String[] args) {
        return messageSource.getMessage(code, args, locale);
    }
}
