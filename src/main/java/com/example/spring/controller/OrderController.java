package com.example.spring.controller;

import com.example.spring.controller.dto.CreateOrder;
import com.example.spring.repository.entity.Order;
import com.example.spring.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public void createOrder(@RequestBody CreateOrder.HttpRequest order) {
        orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> listOrders() {
        return orderService.listOrders();
    }

}
