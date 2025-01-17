package com.example.spring.repository.entity;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false, length = 255)
    private String productName;

    @Column(length = 255)
    private String productOption;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private boolean deleted = false;

    protected OrderItem() {
    }

    public OrderItem(
        final Long productId,
        final String productName,
        final String productOption,
        final BigDecimal unitPrice,
        final int quantity
    ) {
        validateProductId(productId);
        validateProductName(productName);
        validateUnitPrice(unitPrice);
        validateQuantity(quantity);

        this.productId = productId;
        this.productName = productName;
        this.productOption = productOption;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice();
    }

    public void associateWithOrder(final Order order) {
        this.order = order;
    }

    public BigDecimal calculateTotalPrice() {
        return this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }

    private void validateProductId(final Long productId) {
        Assert.notNull(productId, "상품 ID는 null일 수 없습니다.");
        Assert.isTrue(productId > 0, "상품 ID는 0보다 커야 합니다.");
    }

    private void validateProductName(final String productName) {
        Assert.hasText(productName, "상품 이름은 null이거나 비어 있을 수 없습니다.");
    }

    private void validateUnitPrice(final BigDecimal unitPrice) {
        Assert.notNull(unitPrice, "단가는 null일 수 없습니다.");
        Assert.isTrue(unitPrice.compareTo(BigDecimal.ZERO) > 0, "단가는 0보다 커야 합니다.");
    }

    private void validateQuantity(final int quantity) {
        Assert.isTrue(quantity > 0, "수량은 0보다 커야 합니다.");
    }

    public Order getOrder() {
        return order;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productOption='" + productOption + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", deleted=" + deleted +
                '}';
    }

}
