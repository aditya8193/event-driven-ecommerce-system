package org.aditya.common.events;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRefundEvent {

    private Long orderId;

    private Long userId;

    private BigDecimal amount;

}