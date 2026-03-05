package com.aditya.order_service.dto;

import com.aditya.order_service.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrderResponse {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private OrderStatus status;
    private LocalDateTime createdAt;

}