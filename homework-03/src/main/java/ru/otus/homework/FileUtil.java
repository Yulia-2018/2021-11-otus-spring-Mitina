package ru.otus.homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileUtil {

    private FileUtil() {
    }

    public static <T> List<T> getList(String file, Function<String, T> function) {
        List<T> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(function.apply(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
