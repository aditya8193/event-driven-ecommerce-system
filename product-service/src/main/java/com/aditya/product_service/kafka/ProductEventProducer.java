package com.aditya.product_service.kafka;

import com.aditya.product_service.event.ProductCreatedEvent;
import com.aditya.product_service.event.StockReducedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String PRODUCT_CREATED_TOPIC = "product-created";
    private static final String STOCK_REDUCED_TOPIC = "stock-reduced";

    public void publishProductCreated(ProductCreatedEvent productCreatedEvent) {
        log.info("Publishing ProductCreatedEvent for productId={}", productCreatedEvent.getProductId());
        kafkaTemplate.send(PRODUCT_CREATED_TOPIC, productCreatedEvent);
    }

    public void publishStockReduced(StockReducedEvent stockReducedEvent) {
        log.info("Publishing StockReducedEvent for orderId={}", stockReducedEvent.getOrderId());
        kafkaTemplate.send(STOCK_REDUCED_TOPIC, stockReducedEvent);
    }
}