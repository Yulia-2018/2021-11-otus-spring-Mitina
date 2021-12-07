package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.homework.FileUtil;
import ru.otus.homework.domain.Question;

import java.util.List;
import java.util.Locale;

@ConfigurationProperties(prefix = "app")
@Component
public class AppServiceImpl implements AppService {

    private QuestionService questionService;

    private String pathFileAnswers;

    private String language;

    public AppServiceImpl(QuestionService questionService, @Value("${answer.path-file}${app.language}_${answer.file-name}") String pathFileAnswers
    ) {
        this.questionService = questionService;
        this.pathFileAnswers = pathFileAnswers;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void testStudent(MessageSource msg, ConsoleService consoleService) {

        if (language.equals("en")) {
            language = "";
        }
        Locale locale = Locale.forLanguageTag(language);
        consoleService.write(msg.getMessage("app.lastName", null, locale));
        String lastName = consoleService.read();
        consoleService.write(msg.getMessage("app.name", null, locale));
        String name = consoleService.read();
        List<String> answerList = FileUtil.getList(pathFileAnswers, (line) -> line);
        int numberRightAnswers = 0;
        List<Question> questionList = questionService.getAll();
        int numberQuestions = questionList.size();
        for (int i = 0; i < numberQuestions; i++) {
            Question question = questionList.get(i);
            consoleService.write(question.toString());
            String answer = consoleService.read().toLowerCase();
            if (answer.equals(answerList.get(i).toLowerCase())) {
                numberRightAnswers++;
            }
        }
        consoleService.write(msg.getMessage(
                "app.result",
                new String[]{name, lastName, Integer.toString(numberRightAnswers), Integer.toString(numberQuestions)},
                locale));
    }
}
