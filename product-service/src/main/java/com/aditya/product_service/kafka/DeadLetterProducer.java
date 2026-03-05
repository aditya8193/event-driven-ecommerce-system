package com.aditya.product_service.kafka;

import com.aditya.product_service.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeadLetterProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendToDLQ(OrderCreatedEvent event) {

        log.error("Sending event to DLQ orderId={}", event.getOrderId());

        kafkaTemplate.send("order-created-dlq", event);
    }
}