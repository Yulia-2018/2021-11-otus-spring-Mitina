package ru.otus.homework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
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
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Configuration
public class JobConfig {

    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");
    public static final String IMPORT_JOB_NAME = "importDataJob";

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
    public ItemProcessor<Author, R_Author> processorAuthors(AuthorService authorService) {
        return authorService::convert;
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
                .<Author, R_Author>chunk(CHUNK_SIZE)
                .reader(readerAuthors)
                .processor(itemProcessorAuthors)
                .writer(writerAuthors)
                .listener(new ItemReadListener<>() {
                    @Override
                    public void beforeRead() {
                        logger.info("???????????? ????????????");
                    }

                    @Override
                    public void afterRead(Author author) {
                        logger.info("?????????? ????????????");
                    }

                    @Override
                    public void onReadError(Exception e) {
                        logger.info("???????????? ????????????");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    @Override
                    public void beforeWrite(List list) {
                        logger.info("???????????? ????????????");
                    }

                    @Override
                    public void afterWrite(List list) {
                        logger.info("?????????? ????????????");
                    }

                    @Override
                    public void onWriteError(Exception e, List list) {
                        logger.info("???????????? ????????????");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    @Override
                    public void beforeProcess(Author author) {
                        logger.info("???????????? ??????????????????");
                    }

                    @Override
                    public void afterProcess(Author author, R_Author r_author) {
                        logger.info("?????????? ??????????????????");
                    }

                    @Override
                    public void onProcessError(Author author, Exception e) {
                        logger.info("???????????? ??????????????????");
                    }
                })
                .listener(new ChunkListener() {
                    @Override
                    public void beforeChunk(ChunkContext context) {
                        logger.info("???????????? ??????????");
                    }

                    @Override
                    public void afterChunk(ChunkContext context) {
                        logger.info("?????????? ??????????");
                    }

                    @Override
                    public void afterChunkError(ChunkContext context) {
                        logger.info("???????????? ??????????");
                    }
                })
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
    public ItemProcessor<Genre, R_Genre> processorGenres(GenreService genreService) {
        return genreService::convert;
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
                .<Genre, R_Genre>chunk(CHUNK_SIZE)
                .reader(readerGenres)
                .processor(itemProcessorGenres)
                .writer(writerGenres)
                .listener(new ItemReadListener<>() {
                    @Override
                    public void beforeRead() {
                        logger.info("???????????? ????????????");
                    }

                    @Override
                    public void afterRead(Genre genre) {
                        logger.info("?????????? ????????????");
                    }

                    @Override
                    public void onReadError(Exception e) {
                        logger.info("???????????? ????????????");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    @Override
                    public void beforeWrite(List list) {
                        logger.info("???????????? ????????????");
                    }

                    @Override
                    public void afterWrite(List list) {
                        logger.info("?????????? ????????????");
                    }

                    @Override
                    public void onWriteError(Exception e, List list) {
                        logger.info("???????????? ????????????");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    @Override
                    public void beforeProcess(Genre genre) {
                        logger.info("???????????? ??????????????????");
                    }

                    @Override
                    public void afterProcess(Genre genre, R_Genre r_genre) {
                        logger.info("?????????? ??????????????????");
                    }

                    @Override
                    public void onProcessError(Genre genre, Exception e) {
                        logger.info("???????????? ??????????????????");
                    }
                })
                .listener(new ChunkListener() {
                    @Override
                    public void beforeChunk(ChunkContext context) {
                        logger.info("???????????? ??????????");
                    }

                    @Override
                    public void afterChunk(ChunkContext context) {
                        logger.info("?????????? ??????????");
                    }

                    @Override
                    public void afterChunkError(ChunkContext context) {
                        logger.info("???????????? ??????????");
                    }
                })
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
    public ItemProcessor<Book, R_Book> processorBooks(BookService bookService) {
        return bookService::convert;
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
                .<Book, R_Book>chunk(CHUNK_SIZE)
                .reader(readerBooks)
                .processor(itemProcessorBooks)
                .writer(writerBooks)
                .listener(new ItemReadListener<>() {
                    @Override
                    public void beforeRead() {
                        logger.info("???????????? ????????????");
                    }

                    @Override
                    public void afterRead(Book book) {
                        logger.info("?????????? ????????????");
                    }

                    @Override
                    public void onReadError(Exception e) {
                        logger.info("???????????? ????????????");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    @Override
                    public void beforeWrite(List list) {
                        logger.info("???????????? ????????????");
                    }

                    @Override
                    public void afterWrite(List list) {
                        logger.info("?????????? ????????????");
                    }

                    @Override
                    public void onWriteError(Exception e, List list) {
                        logger.info("???????????? ????????????");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    @Override
                    public void beforeProcess(Book book) {
                        logger.info("???????????? ??????????????????");
                    }

                    @Override
                    public void afterProcess(Book book, R_Book r_book) {
                        logger.info("?????????? ??????????????????");
                    }

                    @Override
                    public void onProcessError(Book book, Exception e) {
                        logger.info("???????????? ??????????????????");
                    }
                })
                .listener(new ChunkListener() {
                    @Override
                    public void beforeChunk(ChunkContext context) {
                        logger.info("???????????? ??????????");
                    }

                    @Override
                    public void afterChunk(ChunkContext context) {
                        logger.info("?????????? ??????????");
                    }

                    @Override
                    public void afterChunkError(ChunkContext context) {
                        logger.info("???????????? ??????????");
                    }
                })
                .build();
    }

    @Bean
    public Job importDataJob(Step stepForAuthors, Step stepForGenres, Step stepForBooks) {
        return jobBuilderFactory.get(IMPORT_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(stepForAuthors)
                .next(stepForGenres)
                .next(stepForBooks)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("???????????? job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("?????????? job");
                    }
                })
                .build();
    }
}
