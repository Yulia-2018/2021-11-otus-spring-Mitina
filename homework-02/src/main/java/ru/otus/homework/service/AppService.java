package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.FileUtil;
import ru.otus.homework.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class AppService {

    private QuestionService questionService;

    private String pathFileAnswers;

    public AppService(QuestionService questionService, @Value("${pathFileAnswers}") String pathFileAnswers) {
        this.questionService = questionService;
        this.pathFileAnswers = pathFileAnswers;
    }

    public void testStudent(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.print("Enter your last name: ");
            String lastName = reader.readLine().trim();
            System.out.print("Enter your name: ");
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
            System.out.println(name + " " + lastName + ", the result of your testing: " + numberRightAnswers + " of " + numberQuestions + " questions");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
