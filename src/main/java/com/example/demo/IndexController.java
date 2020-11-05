package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private static final String[] IMAGES = new String[]{
            "baum.jpg",
            "hund.jpg",
            "katze.jpg",
            "mond.jpg"
    };

    @Value("${application.version}")
    private int version;

    @Value("${application.failure-percentage}")
    private int failurePercentage;

    @PostConstruct
    public void init() {
        LOGGER.info("Version: {}", version);
        LOGGER.info("Failure percentage: {}", failurePercentage);
    }

    @GetMapping
    public String index() throws IOException {
        if (ThreadLocalRandom.current().nextInt(100) < failurePercentage) {
            throw new RuntimeException("Boom goes the program");
        }

        try (InputStream stream = IndexController.class.getResourceAsStream("/index.html")) {
            String content = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

            String image = IMAGES[version % IMAGES.length];

            return content
                    .replace("{{ version }}", Integer.toString(version))
                    .replace("{{ image }}", image);
        }
    }
}
