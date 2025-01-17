package com.example.spring.repository.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    @DisplayName("유효한 값으로 OrderItem 생성 성공")
    void createOrderItemWithValidData() {
        // given
        final Long productId = 1L;
        final String productName = "Product A";
        final String productOption = "Option A";
        final BigDecimal unitPrice = BigDecimal.valueOf(100.50);
        final int quantity = 3;

        // when
        final OrderItem orderItem = new OrderItem(productId, productName, productOption, unitPrice, quantity);

        // then
        assertEquals(BigDecimal.valueOf(301.50), orderItem.getTotalPrice());
        assertEquals("Product A", orderItem.getProductName());
    }

    @Test
    @DisplayName("unitPrice가 null일 경우 IllegalArgumentException 발생")
    void createOrderItemWithNullUnitPrice() {
        // given
        final Long productId = 1L;
        final String productName = "Product A";
        final String productOption = "Option A";
        final BigDecimal unitPrice = null;
        final int quantity = 3;

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> new OrderItem(productId, productName, productOption, unitPrice, quantity));
    }

    @Test
    @DisplayName("quantity가 0이거나 음수일 경우 IllegalArgumentException 발생")
    void createOrderItemWithInvalidQuantity() {
        // given
        final Long productId = 1L;
        final String productName = "Product A";
        final String productOption = "Option A";
        final BigDecimal unitPrice = BigDecimal.valueOf(100.50);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> new OrderItem(productId, productName, productOption, unitPrice, 0));
        assertThrows(IllegalArgumentException.class, () -> new OrderItem(productId, productName, productOption, unitPrice, -1));
    }

    @Test
    @DisplayName("Order와의 연관관계 설정 확인")
    void associateWithOrderSetsRelationship() {
        // given
        final OrderItem orderItem = new OrderItem(1L, "Product A", "Option A", BigDecimal.valueOf(100.50), 3);

        // when
        final Order order = new Order(1L, BigDecimal.valueOf(500.00), OrderStatus.PENDING, List.of(orderItem));

        // then
        assertEquals(order, orderItem.getOrder());
    }

    @Test
    @DisplayName("calculateTotalPrice 메서드가 정확한 값을 반환")
    void calculateTotalPriceReturnsCorrectValue() {
        // given
        final OrderItem orderItem = new OrderItem(1L, "Product A", "Option A", BigDecimal.valueOf(99.99), 5);

        // when
        final BigDecimal totalPrice = orderItem.calculateTotalPrice();

        // then
        assertEquals(BigDecimal.valueOf(499.95), totalPrice);
    }

}