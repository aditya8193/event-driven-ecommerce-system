package org.aditya.common.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRefundEvent {

    private Long orderId;

    private Long userId;

    private Double amount;

}