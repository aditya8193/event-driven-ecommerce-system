
package com.aditya.payment_service.kafka;

import com.aditya.payment_service.entity.Payment;
import com.aditya.payment_service.entity.PaymentStatus;
import com.aditya.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aditya.common.events.PaymentRefundEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefundEventConsumer {

    private final PaymentRepository paymentRepository;

    @Transactional
    @KafkaListener(
            topics = "payment-refund",
            groupId = "payment-service-group"
    )
    public void handleRefundEvent(PaymentRefundEvent event) {

        if (event.getOrderId() == null) {
            log.error("Invalid refund event received {}", event);
            return;
        }

        log.info("Processing refund for orderId={}", event.getOrderId());

        Payment payment = paymentRepository.findByOrderId(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.REFUNDED);

        paymentRepository.save(payment);

        log.info("Refund completed for orderId={}", event.getOrderId());
    }
}