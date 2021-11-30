package ru.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework.domain.Question;
import ru.otus.homework.service.QuestionService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@PropertySource("classpath:application.properties")
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuestionService questionService = context.getBean(QuestionService.class);
        String pathFileAnswers = context.getBean(String.class);

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
