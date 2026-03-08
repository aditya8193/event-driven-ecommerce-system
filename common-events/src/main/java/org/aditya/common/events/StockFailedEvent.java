package org.aditya.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockFailedEvent {

    private Long orderId;
    private Long productId;
    private Integer quantity;
    private String reason;

}