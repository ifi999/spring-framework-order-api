package com.example.spring.repository.entity;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private boolean deleted = false;

    protected Order() {
    }

    public Order(
        final Long customerId,
        final BigDecimal totalPrice,
        final OrderStatus status,
        final List<OrderItem> items
    ) {
        validateCustomerId(customerId);
        validateTotalPrice(totalPrice);
        validateOrderStatus(status);
        validateOrderItems(items);

        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.addItems(items);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addItems(final List<OrderItem> items) {
        this.items = items;

        for (final OrderItem item : items) {
            item.associateWithOrder(this);
        }
    }

    private void validateCustomerId(final Long customerId) {
        Assert.notNull(customerId, "고객 ID는 null일 수 없습니다.");
        Assert.isTrue(customerId > 0, "고객 ID는 0보다 커야 합니다.");
    }

    private void validateTotalPrice(final BigDecimal totalPrice) {
        Assert.notNull(totalPrice, "총 가격은 null일 수 없습니다.");
        Assert.isTrue(totalPrice.compareTo(BigDecimal.ZERO) >= 0, "총 가격은 0보다 작을 수 없습니다.");
    }

    private void validateOrderItems(final List<OrderItem> items) {
        Assert.notNull(items, "주문 항목은 null일 수 없습니다.");
        Assert.notEmpty(items, "주문 항목은 비어 있을 수 없습니다.");
    }

    private void validateOrderStatus(final OrderStatus status) {
        Assert.notNull(status, "주문 상태는 null일 수 없습니다.");
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", items=" + items +
                ", deleted=" + deleted +
                '}';
    }

}
