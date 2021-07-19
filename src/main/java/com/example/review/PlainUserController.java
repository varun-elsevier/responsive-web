package com.example.review;

import com.example.review.Review;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("user")
public class PlainUserController {
    final SseEmitter sseEmitter = new SseEmitter(TimeUnit.MINUTES.toMillis(5));

    final List<Review> allReviews = Stream.of(1, 2, 3, 4, 5)
            .map(id -> new Review(id, "test"))
            .collect(Collectors.toList());

    @GetMapping
    public List<Review> getAllReviews() {
        return allReviews;
    }

    @GetMapping("/{id}")
    public Optional<Review> getOneReview(@PathVariable Integer id) {
        Optional<Review> reviewOptional = allReviews.stream().filter(review -> review.id == id)
                .findFirst();
        reviewOptional.ifPresent(review -> {
            try {
                sseEmitter.send(review);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return reviewOptional;
    }

    @GetMapping("/locked")
    public SseEmitter getLocked() {
        return sseEmitter;
    }
}
