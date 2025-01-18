package com.example.spring.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public class CreateOrder {

    public static class HttpRequest {

        private Long customerId;

        private BigDecimal totalPrice;

        private List<OrderItemRequest> items;

        public HttpRequest(final Long customerId, final BigDecimal totalPrice, final List<OrderItemRequest> items) {
            this.customerId = customerId;
            this.totalPrice = totalPrice;
            this.items = items;
        }

        public Long getCustomerId() {
            return customerId;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public List<OrderItemRequest> getItems() {
            return items;
        }

    }

    public static class OrderItemRequest {

        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
        private String option;

        public OrderItemRequest(final Long productId, final String productName, final Integer quantity, final BigDecimal price, final String option) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.option = option;
        }

        public Long getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public String getOption() {
            return option;
        }

    }

}
