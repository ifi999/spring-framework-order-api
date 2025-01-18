package com.example.spring.controller;

import com.example.spring.controller.dto.CreateOrder;
import com.example.spring.repository.entity.Order;
import com.example.spring.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Order API", description = "주문 관리 API")
@RequestMapping("/api/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order getOrder(
        @ApiParam(value = "조회할 주문의 ID", required = true, example = "1")
        @PathVariable Long id
    ) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public void createOrder(
        @ApiParam(value = "생성할 주문 데이터", required = true)
        @RequestBody CreateOrder.HttpRequest order
    ) {
        orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> listOrders() {
        return orderService.listOrders();
    }

}
