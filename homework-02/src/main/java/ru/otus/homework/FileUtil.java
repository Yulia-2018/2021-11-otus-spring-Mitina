package ru.otus.homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

public class FileUtil {

    public static final String FILE_ANSWER = ".\\src\\main\\resources\\answers.csv";

    private FileUtil() {
    }

    public static void writeInList(String file, Consumer<String> consumer) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                consumer.accept(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
