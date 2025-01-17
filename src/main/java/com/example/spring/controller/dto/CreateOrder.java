package com.example.spring.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public class CreateOrder {

    public static class HttpRequest {

        private Long customerId;

        private BigDecimal totalPrice;

        private List<OrderItemRequest> items;

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
