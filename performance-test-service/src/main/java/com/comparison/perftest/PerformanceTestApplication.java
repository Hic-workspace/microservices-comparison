package com.comparison.perftest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PerformanceTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(PerformanceTestApplication.class, args);
    }
}