package com.aditya.order_service.client;

import com.aditya.order_service.dto.PaymentRequest;
import com.aditya.order_service.dto.PaymentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentClient {

    private final WebClient webClient;

    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallBack")
    public PaymentResponse processPayment(PaymentRequest request) {

        return webClient.post()
                .uri("http://payment-service:8084/payments")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .block();
    }

    public PaymentResponse paymentFallBack(PaymentRequest request, Throwable ex) {

        log.error("Payment service unavailable. Fallback triggered.");

        PaymentResponse response = new PaymentResponse();
        response.setStatus("FAILED");

        return response;
    }
}