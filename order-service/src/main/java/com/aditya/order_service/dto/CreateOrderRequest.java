package com.aditya.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {

    @NotNull(message = "Product id must be required")
    private Long productId;

    @NotNull(message = "Quantity must be required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}