package com.aditya.order_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductServiceClient {

    private final RestTemplate restTemplate;

    @Retry(name = "productServiceRetry")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackReduceStock")
    public void reduceStock(Long productId, Integer quantity) {

        String url = "http://localhost:8082/products/"+productId+"/reduce-stock?quantity=" + quantity;

        restTemplate.postForEntity(url, quantity, Void.class);
    }

    public void fallbackReduceStock(Long productId, Integer quantity, Throwable ex) {

        throw new RuntimeException("Product service unavailable");
    }

}