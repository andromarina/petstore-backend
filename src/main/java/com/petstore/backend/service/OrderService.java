package com.petstore.backend.service;

import com.petstore.backend.domain.Order;
import com.petstore.backend.repository.OrderRepository;
import com.petstore.backend.repository.OrderSpecifications;
import com.petstore.backend.web.CreateOrderRequest;
import com.petstore.backend.web.OrderResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional(readOnly = true)
    public OrderResponse findById(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return OrderResponse.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> search(Long petId, String status, Boolean complete) {
        if (petId == null && (status == null || status.isBlank()) && complete == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "At least one search parameter is required: petId, status, complete"
            );
        }
        return orderRepository.findAll(OrderSpecifications.withFilters(petId, status, complete))
                .stream()
                .map(OrderResponse::fromEntity)
                .toList();
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
