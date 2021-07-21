package com.example.review;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("user")
public class PlainUserController {

    final Sinks.Many<Review> sink = Sinks.many().replay().all();

    final Flux<Review> allReviews = Flux.just(1, 2, 3, 4, 5)
            .map(id -> new Review(id, "test"))
            .share();

    @GetMapping
    public Flux<Review> getAllReviews() {
        return allReviews;
    }

    @GetMapping("/{id}")
    public Mono<Review> getOneReview(@PathVariable Integer id) {
        return allReviews.filter(review -> review.id == id)
                .next()
                .doOnNext(review ->
                        sink.emitNext(review, Sinks.EmitFailureHandler.FAIL_FAST)
                );
    }

    @GetMapping(value = "/locked", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Review> getLocked() {

        return sink.asFlux();
    }


}
