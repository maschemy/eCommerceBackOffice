package com.ecommercebackoffice.order.entity;

import com.ecommercebackoffice.admin.entity.Admin;
import com.ecommercebackoffice.common.entity.BaseEntity;
import com.ecommercebackoffice.customer.entity.Customer;
import com.ecommercebackoffice.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    private Integer quantity;
    private Integer totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.READY;
    private String cancelReason;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Order(Admin admin, Customer customer, Product product, String orderNumber, Integer quantity, Integer totalPrice) {
        this.admin = admin;
        this.customer = customer;
        this.product = product;
        this.orderNumber = orderNumber;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // 주문 상태 수정
    public void update(OrderStatus status) {
        if (this.status == OrderStatus.READY && status == OrderStatus.COMPLETE) {
            throw new IllegalStateException("준비중 -> 배송중 -> 배송완료: 절차를 거쳐야 합니다");
        }
        if (status == OrderStatus.CANCEL) {
            throw new IllegalStateException("취소 기능을 사용해주세요");
        }
        this.status = status;
    }

    // 주문 취소
    public void cancel(String cancelReason) {
        if (this.status != OrderStatus.READY) {
            throw new IllegalStateException("준비중일때만 취소 가능합니다");
        }
        this.status = OrderStatus.CANCEL;
        this.cancelReason = cancelReason;
    }
}
