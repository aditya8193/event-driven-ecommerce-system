package com.aditya.order_service.kafka;

import com.aditya.order_service.entity.Order;
import com.aditya.order_service.entity.OrderStatus;
import com.aditya.order_service.event.OrderCreatedEvent;
import com.aditya.order_service.event.StockReducedEvent;
import com.aditya.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "stock-reduced", groupId = "order-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleStockReducedEvent(StockReducedEvent event) {

        log.info("Event received: {}", event);

        log.info(
                "StockReducedEvent received orderId={} success={}",
                event.getOrderId(),
                event.getSuccess()
        );

        if (event.getOrderId() == null) {
            log.error("Invalid event received: {}", event);
            return;
        }

        Order order = orderRepository.findById(event.getOrderId()).orElseThrow(
                () -> new RuntimeException("Order not exist with id: " + event.getOrderId())
        );

        if (event.getSuccess()) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            order.setStatus(OrderStatus.FAILED);
        }

        orderRepository.save(order);
    }
}