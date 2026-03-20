package com.aditya.product_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreatedEvent {

    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

}