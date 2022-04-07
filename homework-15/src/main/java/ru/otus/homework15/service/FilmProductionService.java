package ru.otus.homework15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework15.domain.Film;
import ru.otus.homework15.domain.Frame;

import java.util.ArrayList;
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

    public Film montage(List<Frame> frameList) throws InterruptedException {
        String framesDescription = convertToString(frameList);
        System.out.println("Film editing in progress from frames: " + framesDescription);
        Thread.sleep(2000);
        System.out.println("Film edited from frames: " + framesDescription);
        return new Film("Film " + (int) (Math.random() * 10), framesDescription);
    }

    public List<Film> finalMontage(List<Film> filmList) throws InterruptedException {
        List<Film> readyFilms = new ArrayList<>();
        for (Film film : filmList) {
            System.out.println("Begin final montage of film " + film.getTitle());
            Thread.sleep(3000);
            readyFilms.add(film);
            System.out.println("Film " + film.getTitle() + " is ready");
        }
        return readyFilms;
    }

    private String convertToString(List<Frame> frameList) {
        return frameList.stream().map(Frame::getDescription).collect(Collectors.joining(", "));
    }
}
