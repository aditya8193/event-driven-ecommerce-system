package com.aditya.order_service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockReducedEvent {

    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Boolean success;

}