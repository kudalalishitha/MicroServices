package com.example.order_service.service;

import com.example.order_service.dto.OrderRequest;
import com.example.order_service.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getAllOrders();
}
