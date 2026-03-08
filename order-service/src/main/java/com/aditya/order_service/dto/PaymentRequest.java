package com.aditya.order_service.dto;

import lombok.Data;

@Data
public class PaymentRequest {

    private Long orderId;

    private Long userId;

    private Double amount;

}