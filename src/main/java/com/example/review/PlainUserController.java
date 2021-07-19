package com.example.review;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.function.LongConsumer;

@RestController
@RequestMapping("user")
public class PlainUserController {

    static class Sink implements FluxSink<Review> {


        @Override
        public FluxSink<Review> next(Review review) {
            return null;
        }

        @Override
        public void complete() {

        }

        @Override
        public void error(Throwable e) {

        }

        @Override
        public Context currentContext() {
            return null;
        }

        @Override
        public long requestedFromDownstream() {
            return 0;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public FluxSink<Review> onRequest(LongConsumer consumer) {
            return null;
        }

        @Override
        public FluxSink<Review> onCancel(Disposable d) {
            return null;
        }

        @Override
        public FluxSink<Review> onDispose(Disposable d) {
            return null;
        }
    }

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
                .next();
    }

    @GetMapping("/locked")
    public Flux<Review> getLocked() {
        Flux.create(fluxSink -> fluxSink.)
        return Flux.just();
    }


}
