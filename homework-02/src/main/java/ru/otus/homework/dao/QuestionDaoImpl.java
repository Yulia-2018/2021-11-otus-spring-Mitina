package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final String fileName;

    public QuestionDaoImpl(@Value("questions.csv") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getAll() {

        List<Question> questionList = new ArrayList<>();

        String file = getClass().getClassLoader().getResource(fileName).getFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                questionList.add(new Question(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questionList;
    }
}
