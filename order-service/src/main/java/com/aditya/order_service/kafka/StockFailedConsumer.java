package com.aditya.order_service.kafka;

import com.aditya.order_service.entity.Order;
import com.aditya.order_service.entity.OrderStatus;
import com.aditya.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aditya.common.events.PaymentRefundEvent;
import org.aditya.common.events.StockFailedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockFailedConsumer {

    private final OrderRepository orderRepository;
    private final RefundEventProducer refundEventProducer;

    @KafkaListener(
            topics = "stock-failed",
            groupId = "order-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleStockFailedEvent(StockFailedEvent event) {

        log.warn("StockFailedEvent received orderId={}", event.getOrderId());

        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.FAILED);

        orderRepository.save(order);

        PaymentRefundEvent refundEvent = PaymentRefundEvent.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .amount(order.getAmount())
                .build();

        refundEventProducer.publishRefundEvent(refundEvent);

        log.info("Refund event published for orderId={}", order.getId());
    }
}