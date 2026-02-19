package com.example.order_service.service;

import com.example.order_service.client.ProductClient;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.dto.ProductDto;
import com.example.order_service.exception.InsufficientStockException;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.exception.ProductNotFoundException;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "placeOrderFallback")
    @Retry(name = "productService", fallbackMethod = "placeOrderFallback")
    public OrderResponse placeOrder(OrderRequest request) {

        ProductDto product;

        try {
            //  Get product details
            product = productClient.getProductById(request.getProductId());

            //  Reduce quantity
            productClient.reduceQuantity(request.getProductId(), request.getQuantity());

        } catch (FeignException.NotFound ex) {
            throw new ProductNotFoundException("Product not found with id: " + request.getProductId());
        } catch (FeignException.BadRequest ex) {
            throw new InsufficientStockException("Insufficient stock for product id: " + request.getProductId());
        }

        //  Calculate total amount
        double totalAmount = product.getPrice() * request.getQuantity();

        //  Save order
        Order order = Order.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .totalAmount(totalAmount)
                .build();

        Order saved = orderRepository.save(order);

        //  Return response
        return OrderResponse.builder()
                .orderId(saved.getId())
                .status("SUCCESS")
                .totalAmount(saved.getTotalAmount())
                .build();
    }

    // fallback method for CircuitBreaker + Retry
    public OrderResponse placeOrderFallback(OrderRequest request, Exception ex) {

        return OrderResponse.builder()
                .orderId(null)
                .status("FAILED")
                .totalAmount(0)
                .build();
    }

    @Override
    public OrderResponse getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        return OrderResponse.builder()
                .orderId(order.getId())
                .status("SUCCESS")
                .totalAmount(order.getTotalAmount())
                .build();
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getId())
                        .status("SUCCESS")
                        .totalAmount(order.getTotalAmount())
                        .build())
                .toList();
    }
}
