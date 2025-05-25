package com.comparison.perftest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PerformanceResult {
    private String clientType;
    private long totalDuration;
    private long averageResponseTime;
    private int successCount;
    private int errorCount;
    private double requestsPerSecond;
    private String testType;
}