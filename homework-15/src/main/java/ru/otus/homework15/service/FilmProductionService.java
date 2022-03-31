package ru.otus.homework15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework15.domain.Film;
import ru.otus.homework15.domain.Frame;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmProductionService {

    public List<Frame> selection(List<Frame> frames) throws InterruptedException {
        System.out.println("Select frames from " + convertToString(frames));
        List<Frame> selectionFrames = frames.stream().filter(frame -> !frame.getDouble() && !frame.getDefect()).collect(Collectors.toList());
        Thread.sleep(2000);
        System.out.println("Frames selected: " + convertToString(selectionFrames));
        return selectionFrames;
    }

    public Frame voiceActing(Frame frame) throws InterruptedException {
        String frameDescription = frame.getDescription();
        System.out.println("Frame " + frameDescription + " is being voiced");
        Thread.sleep(1000);
        System.out.println("Frame " + frameDescription + " is voiced");
        return frame;
    }

    public Film montage(List<Frame> frames) throws InterruptedException {
        String framesDescription = convertToString(frames);
        System.out.println("Film editing in progress from frames: " + framesDescription);
        Thread.sleep(2000);
        System.out.println("Film edited from frames: " + framesDescription);
        return new Film("Film " + (int) (Math.random() * 10), framesDescription);
    }

    private String convertToString(List<Frame> frames) {
        return frames.stream().map(Frame::getDescription).collect(Collectors.joining(", "));
    }
}
