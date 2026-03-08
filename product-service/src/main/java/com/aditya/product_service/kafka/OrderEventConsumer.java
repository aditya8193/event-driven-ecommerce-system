package com.aditya.product_service.kafka;

import com.aditya.product_service.entity.ProcessedEvent;
import com.aditya.product_service.exception.InsufficientStockException;
import com.aditya.product_service.repository.ProcessedEventRepository;
import com.aditya.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aditya.common.events.OrderCreatedEvent;
import org.aditya.common.events.StockFailedEvent;
import org.aditya.common.events.StockReducedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final ProductService productService;
    private final ProductEventProducer productEventProducer;
    private final ProcessedEventRepository processedEventRepository;
    private final DeadLetterProducer deadLetterProducer;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000),
            dltTopicSuffix = "-dlq"
    )
    @KafkaListener(topics = "order-created", groupId = "product-service-group")
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {

        log.info(
                "Processing OrderCreatedEvent orderId={} productId={} quantity={}",
                event.getOrderId(),
                event.getProductId(),
                event.getQuantity()
        );

        if (processedEventRepository.existsById(event.getEventId())) {
            log.warn("Duplicate event detected {}", event.getEventId());
            return;
        }

        try {

            productService.reduceStock(
                    event.getProductId(),
                    event.getQuantity()
            );

            StockReducedEvent stockReducedEvent = StockReducedEvent.builder()
                    .orderId(event.getOrderId())
                    .productId(event.getProductId())
                    .quantity(event.getQuantity())
                    .build();

            productEventProducer.publishStockReduced(stockReducedEvent);

        } catch (InsufficientStockException e) {

            log.warn(
                    "Insufficient stock for orderId={} productId={}",
                    event.getOrderId(),
                    event.getProductId(),
                    e
            );

            StockFailedEvent stockFailedEvent = StockFailedEvent.builder()
                    .orderId(event.getOrderId())
                    .productId(event.getProductId())
                    .quantity(event.getQuantity())
                    .reason("INSUFFICIENT_STOCK")
                    .build();

            productEventProducer.publishStockFailed(stockFailedEvent);

        } catch (Exception e) {

            log.error(
                    "Stock reduction failed for orderId={} productId={}",
                    event.getOrderId(),
                    event.getProductId(),
                    e
            );

            deadLetterProducer.sendToDLQ(event);

        } finally {
            processedEventRepository.save(new ProcessedEvent(event.getEventId(), LocalDateTime.now()));
        }
    }
}