package com.aditya.order_service.service;

import com.aditya.order_service.dto.CreateOrderRequest;
import com.aditya.order_service.dto.OrderResponse;
import com.aditya.order_service.entity.Order;
import com.aditya.order_service.entity.OrderStatus;
import com.aditya.order_service.event.OrderCreatedEvent;
import com.aditya.order_service.exception.OrderNotFoundException;
import com.aditya.order_service.kafka.OrderEventProducer;
import com.aditya.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderResponse createOrder(Long user_id, CreateOrderRequest request) {

        Order order = Order.builder()
                .userId(user_id)
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .productId(savedOrder.getProductId())
                .quantity(savedOrder.getQuantity())
                .build();

        orderEventProducer.sendOrderCreatedEvent(event);

        return mapToResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id) throws OrderNotFoundException{

        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("order not exist with id: " + id)
        );

        return mapToResponse(order);

    }

    public List<OrderResponse> getOrderByUserId(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream().map(this::mapToResponse).toList();
    }

    public OrderResponse mapToResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}