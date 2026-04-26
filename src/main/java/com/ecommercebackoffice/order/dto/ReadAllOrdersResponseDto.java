package com.ecommercebackoffice.order.dto;

import com.ecommercebackoffice.order.entity.OrderStatus;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ReadAllOrdersResponseDto {
    private final Long id;
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final int quantity;
    private final int totalPrice;
    private final OrderStatus status;
    private final LocalDateTime createdAt;
    private final String createdBy;

    public ReadAllOrdersResponseDto(Long id, String orderNumber, String customerName, String productName, int quantity, int totalPrice, OrderStatus status, LocalDateTime createdAt, String createdBy) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
