package com.comparison.target.controller;

import com.comparison.target.model.TestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/quick")
    public ResponseEntity<TestData> quickResponse() {
        return ResponseEntity.ok(createTestData("Quick Response"));
    }

    @GetMapping("/delayed")
    public ResponseEntity<TestData> delayedResponse() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500); // Simulated delay
        return ResponseEntity.ok(createTestData("Delayed Response"));
    }

    @PostMapping
    public ResponseEntity<TestData> createData(@RequestBody TestData data) {
        log.info("Received POST request with data: {}", data);
        return ResponseEntity.ok(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestData> updateData(@PathVariable Long id, @RequestBody TestData data) {
        data.setId(id);
        log.info("Updated data with id {}: {}", id, data);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable Long id) {
        log.info("Deleted data with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/large")
    public ResponseEntity<TestData[]> getLargeData() {
        TestData[] largeData = new TestData[1000];
        for (int i = 0; i < 1000; i++) {
            largeData[i] = createTestData("Large Data Item " + i);
        }
        return ResponseEntity.ok(largeData);
    }

    private TestData createTestData(String content) {
        return TestData.builder()
                .id(System.nanoTime())
                .content(content)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}