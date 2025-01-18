package com.example.spring.service;

import com.example.spring.controller.dto.CreateOrder;
import com.example.spring.repository.OrderJpaRepository;
import com.example.spring.repository.entity.Order;
import com.example.spring.repository.entity.OrderItem;
import com.example.spring.repository.entity.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderJpaRepository orderJpaRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("주문 ID로 주문을 조회할 때, 주문이 없으면 IllegalArgumentException 예외 발생")
    void getOrderThrowsExceptionIfOrderNotFound() {
        // given
        final Long orderId = 1L;
        when(orderJpaRepository.findById(orderId)).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> orderService.getOrder(orderId));
        verify(orderJpaRepository, times(1)).findById(orderId);
    }

    @Test
    @DisplayName("주문 ID로 주문을 조회할 때, 주문이 있으면 해당 주문 반환")
    void getOrderReturnsOrderIfExists() {
        // given
        final List<OrderItem> orderItems = List.of(
            new OrderItem(1L, "Product A", "Option A", BigDecimal.valueOf(50), 2)
        );

        final Long orderId = 1L;
        final Order expectedOrder = new Order(1L, BigDecimal.valueOf(100), OrderStatus.PENDING, orderItems);
        when(orderJpaRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        // when
        final Order order = orderService.getOrder(orderId);

        // then
        assertEquals(expectedOrder, order);
        verify(orderJpaRepository, times(1)).findById(orderId);
    }

    @Test
    @DisplayName("주문 생성 시 총 가격이 음수면 IllegalArgumentException 예외 발생")
    void createOrderThrowsExceptionIfTotalPriceIsNegative() {
        // given
        final CreateOrder.HttpRequest request = new CreateOrder.HttpRequest(
            1L,
            BigDecimal.valueOf(-100),
            Collections.emptyList()
        );

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(request));
        verify(orderJpaRepository, never()).save(any());
    }

    @Test
    @DisplayName("주문 생성 시 정상적인 요청일 경우 주문 저장 성공")
    void createOrderSavesOrderIfRequestIsValid() {
        // given
        final List<CreateOrder.OrderItemRequest> orderItems = List.of(new CreateOrder.OrderItemRequest(
            1L,
            "Product A",
            1,
            BigDecimal.TEN,
            "Option A"
        ));
        final CreateOrder.HttpRequest request = new CreateOrder.HttpRequest(1L, BigDecimal.valueOf(100), orderItems);

        // when
        orderService.createOrder(request);

        // then
        verify(orderJpaRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("주문 목록 조회 시 빈 목록 반환")
    void listOrdersReturnsEmptyListIfNoOrdersExist() {
        // given
        when(orderJpaRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        final List<Order> orders = orderService.listOrders();

        // then
        assertTrue(orders.isEmpty());
        verify(orderJpaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("주문 목록 조회 시 모든 주문 반환")
    void listOrdersReturnsAllOrders() {
        // given
        final List<OrderItem> orderItemsA = List.of(
                new OrderItem(1L, "Product A", "Option A", BigDecimal.valueOf(50), 2)
        );

        final List<OrderItem> orderItemsB = List.of(
                new OrderItem(2L, "Product B", "Option B", BigDecimal.valueOf(100), 1)
        );

        final List<Order> expectedOrders = List.of(
                new Order(1L, BigDecimal.valueOf(100), OrderStatus.PENDING, orderItemsA),
                new Order(2L, BigDecimal.valueOf(200), OrderStatus.CONFIRMED, orderItemsB)
        );
        when(orderJpaRepository.findAll()).thenReturn(expectedOrders);

        // when
        final List<Order> actualOrders = orderService.listOrders();

        // then
        assertEquals(expectedOrders, actualOrders);
        verify(orderJpaRepository, times(1)).findAll();
    }

}