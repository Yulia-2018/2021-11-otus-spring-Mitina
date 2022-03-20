package ru.otus.homework.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.domain.mongo.Author;
import ru.otus.homework.domain.mongo.Book;
import ru.otus.homework.domain.mongo.Genre;
import ru.otus.homework.domain.relational.R_Author;
import ru.otus.homework.domain.relational.R_Book;
import ru.otus.homework.domain.relational.R_Genre;
import ru.otus.homework.repository.relational.R_AuthorRepository;
import ru.otus.homework.repository.relational.R_GenreRepository;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Optional;

@Configuration
public class JobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private R_AuthorRepository r_authorRepository;

    @Autowired
    private R_GenreRepository r_genreRepository;

    @Bean
    public MongoItemReader<Author> readerAuthors(MongoTemplate template) {
        return new MongoItemReaderBuilder<Author>()
                .name("mongoItemReaderAuthor")
                .template(template)
                .jsonQuery("{}")
                .targetType(Author.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<Author, R_Author> processorAuthors() {
        return author -> new R_Author(author.getName());
    }

    @Bean
    public JpaItemWriter<R_Author> writerAuthors(EntityManagerFactory factory) {
        return new JpaItemWriterBuilder<R_Author>()
                .entityManagerFactory(factory)
                .build();
    }

    @Bean
    public Step stepForAuthors(MongoItemReader<Author> readerAuthors, JpaItemWriter<R_Author> writerAuthors,
                               ItemProcessor<Author, R_Author> itemProcessorAuthors) {
        return stepBuilderFactory.get("stepForAuthors")
                .<Author, R_Author>chunk(5)
                .reader(readerAuthors)
                .processor(itemProcessorAuthors)
                .writer(writerAuthors)
                .build();
    }

    @Bean
    public MongoItemReader<Genre> readerGenres(MongoTemplate template) {
        return new MongoItemReaderBuilder<Genre>()
                .name("mongoItemReaderGenre")
                .template(template)
                .jsonQuery("{}")
                .targetType(Genre.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<Genre, R_Genre> processorGenres() {
        return genre -> new R_Genre(genre.getTitle());
    }

    @Bean
    public JpaItemWriter<R_Genre> writerGenres(EntityManagerFactory factory) {
        return new JpaItemWriterBuilder<R_Genre>()
                .entityManagerFactory(factory)
                .build();
    }

    @Bean
    public Step stepForGenres(MongoItemReader<Genre> readerGenres, JpaItemWriter<R_Genre> writerGenres,
                              ItemProcessor<Genre, R_Genre> itemProcessorGenres) {
        return stepBuilderFactory.get("stepForGenres")
                .<Genre, R_Genre>chunk(5)
                .reader(readerGenres)
                .processor(itemProcessorGenres)
                .writer(writerGenres)
                .build();
    }

    @Bean
    public MongoItemReader<Book> readerBooks(MongoTemplate template) {
        return new MongoItemReaderBuilder<Book>()
                .name("mongoItemReaderBook")
                .template(template)
                .jsonQuery("{}")
                .targetType(Book.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<Book, R_Book> processorBooks() {
        return book -> {
            String authorName = book.getAuthor().getName();
            String genreTitle = book.getGenre().getTitle();
            Optional<R_Author> r_author = r_authorRepository.getByName(authorName);
            Optional<R_Genre> r_genre = r_genreRepository.getByTitle(genreTitle);
            if (r_author.isPresent() && r_genre.isPresent()) {
                return new R_Book(book.getTitle(), r_author.get(), r_genre.get());
            } else {
                return null;
            }
        };
    }

    @Bean
    public JpaItemWriter<R_Book> writerBooks(EntityManagerFactory factory) {
        return new JpaItemWriterBuilder<R_Book>()
                .entityManagerFactory(factory)
                .build();
    }

    @Bean
    public Step stepForBooks(MongoItemReader<Book> readerBooks, JpaItemWriter<R_Book> writerBooks,
                              ItemProcessor<Book, R_Book> itemProcessorBooks) {
        return stepBuilderFactory.get("stepForBooks")
                .<Book, R_Book>chunk(5)
                .reader(readerBooks)
                .processor(itemProcessorBooks)
                .writer(writerBooks)
                .build();
    }

    @Bean
    public Job importData(Step stepForAuthors, Step stepForGenres, Step stepForBooks) {
        return jobBuilderFactory.get("importData")
                .incrementer(new RunIdIncrementer())
                .start(stepForAuthors)
                .next(stepForGenres)
                .next(stepForBooks)
                .build();
    }
}
