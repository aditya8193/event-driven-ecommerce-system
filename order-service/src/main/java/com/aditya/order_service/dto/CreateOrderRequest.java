package com.aditya.order_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {

    @NotNull(message = "Product id must be required")
    private Long productId;

    @NotNull(message = "Quantity must be required")
    @Size(min = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}