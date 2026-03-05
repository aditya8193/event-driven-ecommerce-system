package com.aditya.product_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreatedEvent {

    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;

}