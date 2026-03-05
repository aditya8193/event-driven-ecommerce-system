package com.aditya.product_service.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent {

    private String eventId;

    private Long orderId;

    private Long userId;

    private Long productId;

    private Integer quantity;

}