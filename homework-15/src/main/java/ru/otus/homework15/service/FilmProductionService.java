package ru.otus.homework15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework15.domain.Film;
import ru.otus.homework15.domain.Frame;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmProductionService {

    public Frame voiceActing(Frame frame) throws InterruptedException {
        String frameDescription = frame.getDescription();
        if (!frame.getDouble() && !frame.getDefect()) {
            System.out.println("Frame " + frameDescription + " is being voiced");
            Thread.sleep(1000);
            System.out.println("Frame " + frameDescription + " is voiced");
        } else {
            System.out.println("Frame " + frameDescription + " not is voiced");
        }
        return frame;
    }

    public Film montage(List<Frame> frames) throws InterruptedException {
        String framesDescription = convertToString(frames);
        System.out.println("Film editing in progress from frames: " + framesDescription);
        Thread.sleep(2000);
        System.out.println("Film edited from frames: " + framesDescription);
        return new Film("Film " + (int) (Math.random() * 10), framesDescription);
    }

    public Film finalMontage(Film film) throws InterruptedException {
        System.out.println("Begin final montage of film " + film.getTitle());
        Thread.sleep(3000);
        System.out.println("Film " + film.getTitle() + " is ready");
        return film;
    }

    private String convertToString(List<Frame> frames) {
        return frames.stream().map(Frame::getDescription).collect(Collectors.joining(", "));
    }
}
