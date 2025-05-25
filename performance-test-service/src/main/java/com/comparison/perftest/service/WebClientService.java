package com.comparison.perftest.service;

import com.comparison.perftest.model.TestData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WebClientService {
    private final WebClient webClient;

    public Mono<TestData> getQuickResponse() {
        return webClient.get()
                .uri("/api/test/quick")
                .retrieve()
                .bodyToMono(TestData.class);
    }

    public Mono<TestData> getDelayedResponse() {
        return webClient.get()
                .uri("/api/test/delayed")
                .retrieve()
                .bodyToMono(TestData.class);
    }

    public Mono<TestData> createData(TestData data) {
        return webClient.post()
                .uri("/api/test")
                .bodyValue(data)
                .retrieve()
                .bodyToMono(TestData.class);
    }

    public Mono<TestData> updateData(Long id, TestData data) {
        return webClient.put()
                .uri("/api/test/{id}", id)
                .bodyValue(data)
                .retrieve()
                .bodyToMono(TestData.class);
    }

    public Mono<Void> deleteData(Long id) {
        return webClient.delete()
                .uri("/api/test/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Flux<TestData> getLargeData() {
        return webClient.get()
                .uri("/api/test/large")
                .retrieve()
                .bodyToFlux(TestData.class);
    }

    public Flux<TestData> getMultipleConcurrentResponses(int count) {
        return Flux.range(0, count)
                .flatMap(i -> getQuickResponse());
    }
}