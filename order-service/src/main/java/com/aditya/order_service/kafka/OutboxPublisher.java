package com.aditya.order_service.kafka;

import com.aditya.order_service.entity.EventStatus;
import com.aditya.order_service.entity.OutboxEvent;
import com.aditya.order_service.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aditya.common.events.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final int MAX_RETRY = 3;

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void publishEvent() {

        List<OutboxEvent> events = outboxEventRepository.findPendingEventsForUpdate();

        for(OutboxEvent event : events) {

            try {
                log.info("Publishing outbox event {}", event.getId());

                OrderCreatedEvent orderEvent = objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);

                kafkaTemplate.send("order-created", orderEvent.getOrderId().toString(), orderEvent);

                event.setStatus(EventStatus.PROCESSED);
                event.setProcessedAt(LocalDateTime.now());

            } catch (Exception e) {

                log.error("Outbox publish failed {}", event.getId());

                int retry = event.getRetryCount() + 1;

                event.setRetryCount(retry);

                if (retry >= MAX_RETRY) {
                    event.setStatus(EventStatus.FAILED);
                } else {
                    event.setStatus(EventStatus.PENDING);
                }
            }
        }
    }
}