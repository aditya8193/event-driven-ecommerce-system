package com.aditya.order_service.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent {

    private String eventId;

    private Long userId;

    private Long orderId;

    private Long productId;

    private Integer quantity;

    private Boolean success;

}