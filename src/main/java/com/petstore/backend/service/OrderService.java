package com.petstore.backend.service;

import com.petstore.backend.domain.Order;
import com.petstore.backend.repository.OrderRepository;
import com.petstore.backend.web.CreateOrderRequest;
import com.petstore.backend.web.OrderResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(OrderResponse::fromEntity).toList();
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        Order order = new Order();
        order.setPetId(request.petId());
        order.setQuantity(request.quantity());
        order.setShipDate(request.shipDate());
        order.setStatus(request.status());
        order.setComplete(request.complete());
        Order saved = orderRepository.save(order);
        return OrderResponse.fromEntity(saved);
    }
}
