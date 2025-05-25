package com.comparison.perftest.client;

import com.comparison.perftest.model.TestData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "target-service", url = "${target.service.url}")
public interface TargetServiceFeignClient {

    @GetMapping("/api/test/quick")
    ResponseEntity<TestData> getQuickResponse();

    @GetMapping("/api/test/delayed")
    ResponseEntity<TestData> getDelayedResponse();

    @PostMapping("/api/test")
    ResponseEntity<TestData> createData(@RequestBody TestData data);

    @PutMapping("/api/test/{id}")
    ResponseEntity<TestData> updateData(@PathVariable("id") Long id, @RequestBody TestData data);

    @DeleteMapping("/api/test/{id}")
    ResponseEntity<Void> deleteData(@PathVariable("id") Long id);

    @GetMapping("/api/test/large")
    ResponseEntity<TestData[]> getLargeData();
}