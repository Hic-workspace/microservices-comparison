package com.comparison.perftest.service;

import com.comparison.perftest.client.TargetServiceFeignClient;
import com.comparison.perftest.model.PerformanceResult;
import com.comparison.perftest.model.TestData;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceTestService {
    private final WebClientService webClientService;
    private final TargetServiceFeignClient feignClient;
    private final MeterRegistry meterRegistry;

    public PerformanceResult testOpenFeign(int requestCount, String testType) {
        long startTime = System.currentTimeMillis();
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);
        List<Long> responseTimes = new ArrayList<>();

        for (int i = 0; i < requestCount; i++) {
            long requestStartTime = System.currentTimeMillis();
            try {
                switch (testType) {
                    case "quick" -> feignClient.getQuickResponse();
                    case "delayed" -> feignClient.getDelayedResponse();
                    case "large" -> feignClient.getLargeData();
                    default -> throw new IllegalArgumentException("Invalid test type");
                }
                successCount.incrementAndGet();
                responseTimes.add(System.currentTimeMillis() - requestStartTime);
            } catch (Exception e) {
                errorCount.incrementAndGet();
                log.error("Error in OpenFeign request", e);
            }
        }

        long totalDuration = System.currentTimeMillis() - startTime;
        double avgResponseTime = responseTimes.stream().mapToLong(Long::valueOf).average().orElse(0.0);
        double requestsPerSecond = (double) successCount.get() / (totalDuration / 1000.0);

        return PerformanceResult.builder()
                .clientType("OpenFeign")
                .totalDuration(totalDuration)
                .averageResponseTime((long) avgResponseTime)
                .successCount(successCount.get())
                .errorCount(errorCount.get())
                .requestsPerSecond(requestsPerSecond)
                .testType(testType)
                .build();
    }

    public Mono<PerformanceResult> testWebClient(int requestCount, String testType) {
        long startTime = System.currentTimeMillis();
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);
        List<Long> responseTimes = new ArrayList<>();

        Flux<?> testFlux = switch (testType) {
            case "quick" -> webClientService.getMultipleConcurrentResponses(requestCount);
            case "delayed" -> Flux.range(0, requestCount).flatMap(i -> webClientService.getDelayedResponse());
            case "large" -> Flux.range(0, requestCount)
                    .flatMap(i -> webClientService.getLargeData().collectList());
            default -> throw new IllegalArgumentException("Invalid test type");
        };

        return testFlux
                .map(response -> {
                    successCount.incrementAndGet();
                    return response;
                })
                .doOnError(e -> {
                    errorCount.incrementAndGet();
                    log.error("Error in WebClient request", e);
                })
                .then(Mono.fromCallable(() -> {
                    long totalDuration = System.currentTimeMillis() - startTime;
                    double requestsPerSecond = (double) successCount.get() / (totalDuration / 1000.0);

                    return PerformanceResult.builder()
                            .clientType("WebClient")
                            .totalDuration(totalDuration)
                            .averageResponseTime(totalDuration / requestCount)
                            .successCount(successCount.get())
                            .errorCount(errorCount.get())
                            .requestsPerSecond(requestsPerSecond)
                            .testType(testType)
                            .build();
                }));
    }
}