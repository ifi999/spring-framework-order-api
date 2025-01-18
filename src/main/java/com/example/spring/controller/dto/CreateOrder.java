package com.example.spring.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

public class CreateOrder {

    @ApiModel(description = "주문 생성 요청")
    public static class HttpRequest {

        @ApiModelProperty(value = "고객 ID", example = "1", required = true)
        private Long customerId;

        @ApiModelProperty(value = "주문 총 금액", example = "100.00", required = true)
        private BigDecimal totalPrice;

        @ApiModelProperty(value = "주문 항목 리스트", required = true)
        private List<OrderItemRequest> items;

        protected HttpRequest() {
        }

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

    @ApiModel(description = "주문 항목 요청")
    public static class OrderItemRequest {

        @ApiModelProperty(value = "상품 ID", example = "101", required = true)
        private Long productId;
        @ApiModelProperty(value = "상품 이름", example = "Product A", required = true)
        private String productName;
        @ApiModelProperty(value = "주문 수량", example = "2", required = true)
        private Integer quantity;
        @ApiModelProperty(value = "상품 단가", example = "20.00", required = true)
        private BigDecimal price;
        @ApiModelProperty(value = "옵션 정보", example = "Color: Red")
        private String option;

        protected OrderItemRequest() {
        }

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
