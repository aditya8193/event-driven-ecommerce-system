package com.aditya.order_service.config;

import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RetryConfig {

    private final RetryRegistry retryRegistry;

    @PostConstruct
    public void init() {

        retryRegistry.retry("productServiceRetry")
                .getEventPublisher()
                .onRetry(event -> {
                    System.out.println("Retry attempt: " + event.getNumberOfRetryAttempts());
                });
    }
}