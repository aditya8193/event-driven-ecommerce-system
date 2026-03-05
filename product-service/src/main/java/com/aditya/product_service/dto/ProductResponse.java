package com.aditya.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private LocalDateTime createdAt;

}