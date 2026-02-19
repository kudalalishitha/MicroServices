package com.example.order_service.client;

import com.example.order_service.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// Feign client to call Product Service
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    // Get product by id
    @GetMapping("/products/{id}")
    ProductDto getProductById(@PathVariable Long id);

    // Reduce product quantity
    @PutMapping("/products/reduce/{id}")
    ProductDto reduceQuantity(@PathVariable Long id,
                              @RequestParam int quantity);
}
