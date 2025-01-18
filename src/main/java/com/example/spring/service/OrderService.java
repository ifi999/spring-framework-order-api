package com.example.spring.service;

import com.example.spring.controller.dto.CreateOrder;
import com.example.spring.repository.OrderJpaRepository;
import com.example.spring.repository.entity.Order;
import com.example.spring.repository.entity.OrderItem;
import com.example.spring.repository.entity.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;

    public OrderService(final OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    public Order getOrder(final Long id) {
        return orderJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order is not found. Order ID: " + id));
    }

    public void createOrder(final CreateOrder.HttpRequest request) {
        final List<OrderItem> orderItems = request.getItems().stream()
            .map(item -> new OrderItem(
                item.getProductId(),
                item.getProductName(),
                item.getOption(),
                item.getPrice(),
                item.getQuantity()
            ))
            .toList();

        final Order order = new Order(
            request.getCustomerId(),
            request.getTotalPrice(),
            OrderStatus.PENDING,
            orderItems
        );

        orderJpaRepository.save(order);
    }

    public List<Order> listOrders() {
        return orderJpaRepository.findAll();
    }

}
