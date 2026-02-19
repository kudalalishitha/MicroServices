package com.example.product_service.service;

import com.example.product_service.dto.ProductRequest;
import com.example.product_service.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse addProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    ProductResponse reduceQuantity(Long id, int quantity);
}
