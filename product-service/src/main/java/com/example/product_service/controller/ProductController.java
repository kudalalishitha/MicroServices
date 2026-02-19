package com.example.product_service.controller;

import com.example.product_service.dto.ProductRequest;
import com.example.product_service.dto.ProductResponse;
import com.example.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Add Product
    @PostMapping
    public ProductResponse addProduct(@RequestBody ProductRequest request) {
        return productService.addProduct(request);
    }

    // Get All Products
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get Product By Id
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // Update Product
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id,
                                         @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully!";
    }

    // Reduce Product Quantity (Internal API for Order Service)
    @PutMapping("/reduce/{id}")
    public ProductResponse reduceQuantity(@PathVariable Long id,
                                          @RequestParam int quantity) {
        return productService.reduceQuantity(id, quantity);
    }
}
