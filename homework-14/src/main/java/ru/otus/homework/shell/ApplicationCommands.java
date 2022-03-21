package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.repository.mongo.AuthorRepository;
import ru.otus.homework.repository.mongo.BookRepository;
import ru.otus.homework.repository.mongo.GenreRepository;
import ru.otus.homework.repository.relational.R_AuthorRepository;
import ru.otus.homework.repository.relational.R_BookRepository;
import ru.otus.homework.repository.relational.R_GenreRepository;

import static ru.otus.homework.config.JobConfig.IMPORT_JOB_NAME;

@RequiredArgsConstructor
@ShellComponent
public class ApplicationCommands {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    private final R_AuthorRepository r_authorRepository;
    private final R_GenreRepository r_genreRepository;
    private final R_BookRepository r_bookRepository;

    private final Job importDataJob;

    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-jl")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(importDataJob, new JobParametersBuilder().toJobParameters());
        System.out.println(execution);
    }

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() throws Exception {
        Long executionId = jobOperator.start(IMPORT_JOB_NAME, "");
        System.out.println(jobOperator.getSummary(executionId));
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_JOB_NAME));
    }

    @ShellMethod(value = "Get all authors (relational)", key = "allAr")
    private void getAllRAuthors() {
        r_authorRepository.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get all genres (relational)", key = "allGr")
    private void getAllRGenres() {
        r_genreRepository.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get all books (relational)", key = "allBr")
    private void getAllRBooks() {
        r_bookRepository.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get all authors (mongo)", key = "allAm")
    private void getAllAuthors() {
        authorRepository.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get all genres (mongo)", key = "allGm")
    private void getAllGenres() {
        genreRepository.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get all books (mongo)", key = "allBm")
    private void getAllBooks() {
        bookRepository.findAll().forEach(System.out::println);
    }
}
