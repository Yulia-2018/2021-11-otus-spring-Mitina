package ru.otus.homework.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsCounter {

    private final MeterRegistry registry;

    public MetricsCounter(MeterRegistry registry) {
        this.registry = registry;
    }

    @GetMapping(value = "/test")
    public void counter() {
        registry.counter("test.counter").increment();
    }
}
