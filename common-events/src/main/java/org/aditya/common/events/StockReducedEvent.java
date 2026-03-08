package org.aditya.common.events;

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

}