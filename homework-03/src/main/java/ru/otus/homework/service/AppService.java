package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.homework.FileUtil;
import ru.otus.homework.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

@ConfigurationProperties(prefix = "app")
@Component
public class AppService {

    private QuestionService questionService;

    private String pathFileAnswers;

    private String language;

    public AppService(QuestionService questionService, @Value("${answer.path-file}${app.language}_${answer.file-name}") String pathFileAnswers) {
        this.questionService = questionService;
        this.pathFileAnswers = pathFileAnswers;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void testStudent(MessageSource msg) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            if (language.equals("en")) {
                language = "";
            }
            Locale locale = Locale.forLanguageTag(language);
            System.out.println(msg.getMessage("app.lastName", null, locale));
            String lastName = reader.readLine().trim();
            System.out.println(msg.getMessage("app.name", null, locale));
            String name = reader.readLine().trim();

            List<String> answerList = FileUtil.getList(pathFileAnswers, (line) -> line);
            int numberRightAnswers = 0;
            List<Question> questionList = questionService.getAll();
            int numberQuestions = questionList.size();
            for (int i = 0; i < numberQuestions; i++) {
                Question question = questionList.get(i);
                System.out.println(question);
                String answer = reader.readLine().trim().toLowerCase();
                if (answer.equals(answerList.get(i).toLowerCase())) {
                    numberRightAnswers++;
                }
            }
            System.out.println(msg.getMessage(
                    "app.result",
                    new String[]{name, lastName, Integer.toString(numberRightAnswers), Integer.toString(numberQuestions)},
                    locale));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
