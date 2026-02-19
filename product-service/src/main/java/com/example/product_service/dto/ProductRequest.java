package com.example.product_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private double price;
    private int quantity;
}
