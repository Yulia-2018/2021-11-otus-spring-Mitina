package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.homework.FileUtil;
import ru.otus.homework.domain.Question;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final String fileName;

    public QuestionDaoImpl(@Value("${fileName}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getAll() {

        List<Question> questionList = new ArrayList<>();
        String file = getClass().getClassLoader().getResource(fileName).getFile();
        FileUtil.writeInList(file, (line) -> questionList.add(new Question(line)));
        return questionList;
    }
}
