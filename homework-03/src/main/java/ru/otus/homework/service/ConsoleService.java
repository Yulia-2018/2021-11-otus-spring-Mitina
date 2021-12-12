package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ConsoleService {

    private final Scanner scanner;

    public ConsoleService() {
        this.scanner = new Scanner(System.in);
    }

    public String read() {
        return scanner.nextLine().trim();
    }

    public void write(String message) {
        System.out.println(message);
    }
}
