package com.aditya.product_service.kafka;

import com.aditya.product_service.event.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aditya.common.events.StockFailedEvent;
import org.aditya.common.events.StockReducedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventProducer {

    private final KafkaTemplate<String, ProductCreatedEvent> productCreatedEventKafkaTemplate;
    private final KafkaTemplate<String, StockReducedEvent> stockReducedEventKafkaTemplate;
    private final KafkaTemplate<String, StockFailedEvent> stockFailedEventKafkaTemplate;

    private static final String PRODUCT_CREATED_TOPIC = "product-created";
    private static final String STOCK_REDUCED_TOPIC = "stock-reduced";
    private static final String STOCK_FAILED_TOPIC = "stock-failed";

    public void publishProductCreated(ProductCreatedEvent productCreatedEvent) {
        log.info("Publishing ProductCreatedEvent for productId={}", productCreatedEvent.getProductId());
        productCreatedEventKafkaTemplate.send(PRODUCT_CREATED_TOPIC, productCreatedEvent);
    }

    public void publishStockReduced(StockReducedEvent stockReducedEvent) {
        log.info("Publishing StockReducedEvent for orderId={}", stockReducedEvent.getOrderId());
        stockReducedEventKafkaTemplate.send(STOCK_REDUCED_TOPIC, stockReducedEvent);
    }

    public void publishStockFailed(StockFailedEvent event) {
        log.info("Publishing StockFailedEvent for orderId={}", event.getOrderId());
        stockFailedEventKafkaTemplate.send(STOCK_FAILED_TOPIC, event);
    }
}