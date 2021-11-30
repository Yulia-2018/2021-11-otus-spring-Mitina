package ru.otus.homework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    String pathFileAnswers(@Value("${pathFileAnswers}") String path) {
        return path;
    }
}
