package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleIOService implements IOService {

    private final Scanner scanner;

    private final PrintStream out;

    public ConsoleIOService() {
        this.scanner = new Scanner(System.in);
        this.out = System.out;
    }

    @Override
    public String read() {
        return scanner.nextLine().trim();
    }

    @Override
    public void write(String message) {
        out.println(message);
    }
}
