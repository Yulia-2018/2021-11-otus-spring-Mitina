package ru.otus.homework15.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework15.domain.Film;
import ru.otus.homework15.domain.Frame;

import java.util.List;

@MessagingGateway
public interface FilmStudio {

    @Gateway(requestChannel = "framesChannel", replyChannel = "filmsChannel")
    Film process(List<Frame> frames);
}
