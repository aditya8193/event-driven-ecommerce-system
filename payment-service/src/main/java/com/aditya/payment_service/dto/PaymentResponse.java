package com.aditya.payment_service.dto;

import com.aditya.payment_service.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private Long paymentId;

    private Long orderId;

    private PaymentStatus status;
}