package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.homework.FileUtil;
import ru.otus.homework.domain.Question;

import java.util.List;

@Component
public class AppServiceImpl implements AppService {

    private final QuestionService questionService;

    private final String pathFileAnswers;

    private final MessageService messageService;

    private final IOService ioService;

    public AppServiceImpl(QuestionService questionService,
                          @Value("${answer.path-file}${app.language}_${answer.file-name}") String pathFileAnswers,
                          MessageService messageService,
                          IOService ioService) {
        this.questionService = questionService;
        this.pathFileAnswers = pathFileAnswers;
        this.messageService = messageService;
        this.ioService = ioService;
    }

    @Override
    public void testStudent() {

        ioService.write(messageService.getMessage("app.lastName", null));
        String lastName = ioService.read();
        ioService.write(messageService.getMessage("app.name", null));
        String name = ioService.read();
        List<String> answerList = FileUtil.getList(pathFileAnswers, (line) -> line);
        int numberRightAnswers = 0;
        List<Question> questionList = questionService.getAll();
        int numberQuestions = questionList.size();
        for (int i = 0; i < numberQuestions; i++) {
            Question question = questionList.get(i);
            ioService.write(question.toString());
            String answer = ioService.read().toLowerCase();
            if (answer.equals(answerList.get(i).toLowerCase())) {
                numberRightAnswers++;
            }
        }
        ioService.write(messageService.getMessage("app.result", new String[]{name, lastName, Integer.toString(numberRightAnswers), Integer.toString(numberQuestions)}));
    }
}
