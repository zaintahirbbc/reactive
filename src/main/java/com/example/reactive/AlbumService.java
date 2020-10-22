package com.example.reactive;

import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AlbumService {


    private final ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;
    private final WebClient.Builder webClient;

    public AlbumService(ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory, WebClient.Builder webClient) {
        this.reactiveCircuitBreakerFactory = reactiveCircuitBreakerFactory;
        this.webClient = webClient;
    }


    public Mono<String> getAlbumMono() {
//        String url = "https://jsonplaceholder.typicode.com/albums";
        String url = "http://localhost:1234/fake";
        return webClient.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class).transform(it -> {
                    ReactiveCircuitBreaker reactiveCircuitBreaker = reactiveCircuitBreakerFactory.create("slow");
                    return reactiveCircuitBreaker.run(it, throwable -> fallback());
                });
    }

    public Mono<String> fallback() {
        String fallback = "we are in the fallback";
        return Mono.just(fallback);
    }


}
