package com.example.product_service.service;

import com.example.product_service.dto.ProductRequest;
import com.example.product_service.dto.ProductResponse;
import com.example.product_service.exception.InsufficientStockException;
import com.example.product_service.exception.ProductNotFoundException;
import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Service class for Product business logic
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    // Repository used to interact with DB
    private final ProductRepository productRepository;

    // Add a new product
    @Override
    public ProductResponse addProduct(ProductRequest request) {

        // Convert request DTO to entity
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        // Save product in DB
        Product saved = productRepository.save(product);

        // Return response DTO
        return mapToResponse(saved);
    }

    // Get all products
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse) // Convert each entity to response DTO
                .toList();
    }

    // Get product by id
    @Override
    public ProductResponse getProductById(Long id) {

        // Find product, else throw exception
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        return mapToResponse(product);
    }

    // Update product by id
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        // Check product exists
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        // Update values
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        // Save updated product
        Product updated = productRepository.save(product);

        return mapToResponse(updated);
    }

    // Delete product by id
    @Override
    public void deleteProduct(Long id) {

        // Check product exists
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        // Delete product from DB
        productRepository.delete(product);
    }

    // Reduce product quantity (used by Order Service)
    @Override
    public ProductResponse reduceQuantity(Long id, int quantity) {

        // Check product exists
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        // Quantity should be valid
        if (quantity <= 0) {
            throw new InsufficientStockException("Quantity must be greater than 0");
        }

        // Check stock availability
        if (product.getQuantity() < quantity) {
            throw new InsufficientStockException("Insufficient stock for product id: " + id);
        }

        // Reduce stock
        product.setQuantity(product.getQuantity() - quantity);

        // Save updated product
        Product updated = productRepository.save(product);

        return mapToResponse(updated);
    }

    // Helper method to convert Product entity to ProductResponse DTO
    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}
