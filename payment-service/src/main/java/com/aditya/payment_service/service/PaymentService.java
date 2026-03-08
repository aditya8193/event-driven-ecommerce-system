package com.aditya.payment_service.service;

import com.aditya.payment_service.dto.PaymentRequest;
import com.aditya.payment_service.dto.PaymentResponse;
import com.aditya.payment_service.entity.Payment;
import com.aditya.payment_service.entity.PaymentStatus;
import com.aditya.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentResponse processPayment(PaymentRequest request) {

        boolean success = simulatePayment();

        PaymentStatus status = success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;

        Payment payment = Payment.builder()
                .userId(request.getUserId())
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .status(status)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentResponse.builder()
                .paymentId(savedPayment.getId())
                .orderId(savedPayment.getOrderId())
                .status(savedPayment.getStatus())
                .build();
    }

    private boolean simulatePayment() {

        Random random = new Random();

        return random.nextBoolean();
    }
}