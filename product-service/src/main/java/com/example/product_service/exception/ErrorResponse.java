package com.example.product_service.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String timestamp;
    private String message;
    private int status;
}
