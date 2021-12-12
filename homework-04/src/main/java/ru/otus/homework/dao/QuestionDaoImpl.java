package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.homework.FileUtil;
import ru.otus.homework.domain.Question;

import java.util.List;

@Component
public class QuestionDaoImpl implements QuestionDao {

    private final String fileName;

    public QuestionDaoImpl(@Value("${app.language}_${question.file-name}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getAll() {

        String file = getClass().getClassLoader().getResource(fileName).getFile();
        return FileUtil.getList(file, (line) -> Question.builder().text(line).build());
    }
}
