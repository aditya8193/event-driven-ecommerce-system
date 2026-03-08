package com.aditya.order_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aditya.common.events.PaymentRefundEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefundEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String PAYMENT_REFUND_TOPIC = "payment-refund";

    public void publishRefundEvent(PaymentRefundEvent event) {

        log.info("Publishing PaymentRefundEvent for orderId={}", event.getOrderId());

        kafkaTemplate.send(PAYMENT_REFUND_TOPIC, event.getOrderId().toString(), event);
    }
}