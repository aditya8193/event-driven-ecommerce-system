package com.aditya.order_service.controller;

import com.aditya.order_service.dto.CreateOrderRequest;
import com.aditya.order_service.dto.OrderResponse;
import com.aditya.order_service.entity.Order;
import com.aditya.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Long mockUserId = 1L;
        return ResponseEntity.ok(orderService.createOrder(mockUserId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrderByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrderByUserId(userId));
    }
}
