package com.example.spring.repository.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("null 리스트를 추가하려고 할 때 IllegalArgumentException 예외 발생")
    void addItemsWithNullList() {
        // given
        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> new Order(1L, BigDecimal.TEN, OrderStatus.PENDING, null));
    }

    @Test
    @DisplayName("주문 항목이 비어 있는 경우 IllegalArgumentException 예외 발생")
    void orderWithEmptyItemsThrowsException() {
        // given
        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> new Order(1L, BigDecimal.TEN, OrderStatus.PENDING, List.of()));
    }

    @Test
    @DisplayName("Order와 OrderItem의 연관관계가 제대로 설정되는지 확인")
    void addItemsSetsRelationshipCorrectly() {
        // given
        final OrderItem item1 = new OrderItem(1L, "Product A", "Option A", BigDecimal.TEN, 2);
        final List<OrderItem> items = List.of(item1);

        // when
        final Order order = new Order(1L, BigDecimal.TEN, OrderStatus.PENDING, items);

        // then
        assertEquals(order, item1.getOrder());
    }

    @Test
    @DisplayName("총 가격이 음수인 경우 IllegalArgumentException 예외 발생")
    void orderWithNegativeTotalPriceThrowsException() {
        // given
        final List<OrderItem> items = List.of(new OrderItem(1L, "Product A", "Option A", BigDecimal.TEN, 1));

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> new Order(1L, BigDecimal.valueOf(-10), OrderStatus.PENDING, items));
    }

    @Test
    @DisplayName("주문 상태가 null인 경우 IllegalArgumentException 예외 발생")
    void orderWithNullStatusThrowsException() {
        // given
        final List<OrderItem> items = List.of(new OrderItem(1L, "Product A", "Option A", BigDecimal.TEN, 1));

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> new Order(1L, BigDecimal.TEN, null, items));
    }

}