package com.comparison.perftest.controller;

import com.comparison.perftest.model.PerformanceResult;
import com.comparison.perftest.service.PerformanceTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
public class PerformanceTestController {
    private final PerformanceTestService performanceTestService;

    @GetMapping("/test")
    public Mono<PerformanceResult[]> runComparison(
            @RequestParam(defaultValue = "100") int requestCount,
            @RequestParam(defaultValue = "quick") String testType) {

        return Mono.zip(
                performanceTestService.testWebClient(requestCount, testType),
                Mono.fromCallable(() -> performanceTestService.testOpenFeign(requestCount, testType))
        ).map(tuple -> new PerformanceResult[]{tuple.getT1(), tuple.getT2()});
    }
}