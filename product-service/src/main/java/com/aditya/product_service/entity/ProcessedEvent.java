package com.aditya.product_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProcessedEvent {

    @Id
    private String eventId;

    private LocalDateTime processedAt;

}