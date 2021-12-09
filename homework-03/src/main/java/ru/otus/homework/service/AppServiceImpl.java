package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.homework.FileUtil;
import ru.otus.homework.domain.Question;

import java.util.List;

@Component
public class AppServiceImpl implements AppService {

    private QuestionService questionService;

    private String pathFileAnswers;

    private MessageService messageService;

    private ConsoleService consoleService;

    public AppServiceImpl(QuestionService questionService,
                          @Value("${answer.path-file}${app.language}_${answer.file-name}") String pathFileAnswers,
                          MessageService messageService,
                          ConsoleService consoleService) {
        this.questionService = questionService;
        this.pathFileAnswers = pathFileAnswers;
        this.messageService = messageService;
        this.consoleService = consoleService;
    }

    @Override
    public void testStudent() {

        consoleService.write(messageService.getMessage("app.lastName", null));
        String lastName = consoleService.read();
        consoleService.write(messageService.getMessage("app.name", null));
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
        consoleService.write(messageService.getMessage("app.result", new String[]{name, lastName, Integer.toString(numberRightAnswers), Integer.toString(numberQuestions)}));
    }
}
