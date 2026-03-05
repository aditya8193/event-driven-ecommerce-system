package com.aditya.product_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockReducedEvent {

    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Boolean success;

}