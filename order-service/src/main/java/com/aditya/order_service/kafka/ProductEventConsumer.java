package com.aditya.order_service.kafka;

import com.aditya.order_service.entity.Order;
import com.aditya.order_service.entity.OrderStatus;
import com.aditya.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aditya.common.events.StockReducedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "stock-reduced", groupId = "order-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleStockReducedEvent(StockReducedEvent event) {

        log.info("StockReducedEvent received orderId={}", event.getOrderId());

        if (event.getOrderId() == null) {
            log.error("Invalid event received: {}", event);
            return;
        }

        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new IllegalStateException("Order not found: " + event.getOrderId()));

        order.setStatus(OrderStatus.CONFIRMED);

        orderRepository.save(order);

        log.info("Order {} marked as CONFIRMED", order.getId());
    }
}