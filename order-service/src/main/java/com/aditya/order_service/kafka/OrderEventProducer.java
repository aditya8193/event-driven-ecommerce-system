package com.aditya.order_service.kafka;

import com.aditya.order_service.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {

        log.info("Publishing OrderCreatedEvent for orderId={}", event.getOrderId());

        kafkaTemplate.send("order-created", event);
    }

}