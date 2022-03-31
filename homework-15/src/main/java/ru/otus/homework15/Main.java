package ru.otus.homework15;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.homework15.domain.Film;
import ru.otus.homework15.domain.Frame;
import ru.otus.homework15.integration.FilmStudio;

import java.util.List;
import java.util.Map;

@IntegrationComponentScan
@ComponentScan
@Configuration
@EnableIntegration
public class Main {

    private static final Map<Integer, List<Frame>> MAP_FRAMES = Map.of(
            1, List.of(
                    new Frame("Work", false, false),
                    new Frame("Boss", false, true),
                    new Frame("Light Industry Department", false, false),
                    new Frame("Light Industry Department 2", true, false),
                    new Frame("Children: boy and boy", false, false),
                    new Frame("Happy end", false, false)
            ),
            2, List.of(
                    new Frame("Bath", false, true),
                    new Frame("Airplane", false, false),
                    new Frame("Leningrad", false, false),
                    new Frame("New Year", false, false),
                    new Frame("Airplane 2", true, false),
                    new Frame("New love", false, false),
                    new Frame("Airplane 3", true, false),
                    new Frame("Happy end", false, false)
            ));

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(5000).get();
    }

    @Bean
    public QueueChannel framesChannel() {
        return MessageChannels.queue(3).get();
    }

    @Bean
    public PublishSubscribeChannel filmsChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow filmStudioFlow() {
        return IntegrationFlows.from("framesChannel")
                .handle("filmProductionService", "selection")
                .split()
                //.<Frame>filter(frame -> !frame.getDouble())
                //.<Frame>filter(frame -> !frame.getDefect())
                .handle("filmProductionService", "voiceActing")
                .aggregate()
                .handle("filmProductionService", "montage")
                .channel("filmsChannel")
                .get();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        FilmStudio filmStudio = context.getBean(FilmStudio.class);

        for (Map.Entry<Integer, List<Frame>> entry : MAP_FRAMES.entrySet()) {
            System.out.println("Start of film production");
            Film film = filmStudio.process(entry.getValue());
            System.out.println("Film " + film.getTitle() + " (" + film.getShortDescription() + ") is ready");
        }
    }
}
