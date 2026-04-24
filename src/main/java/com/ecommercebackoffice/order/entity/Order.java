package com.ecommercebackoffice.order.entity;

import com.ecommercebackoffice.admin.entity.Admin;
import com.ecommercebackoffice.common.entity.BaseEntity;
import com.ecommercebackoffice.customer.entity.Customer;
import com.ecommercebackoffice.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@SQLDelete(sql = "UPDATE orders SET deletedAt = NOW() WHERE id = ?")
@SQLRestriction("deletedAt IS NULL")
@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    private int quantity;
    private int totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.READY;
    private String cancelReason;
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Order(Admin admin, Customer customer, Product product, String orderNumber, int quantity, int totalPrice) {
        this.admin = admin;
        this.customer = customer;
        this.product = product;
        this.orderNumber = orderNumber;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
