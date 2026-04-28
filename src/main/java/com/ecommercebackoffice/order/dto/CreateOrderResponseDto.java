package com.ecommercebackoffice.order.dto;

import com.ecommercebackoffice.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateOrderResponseDto {
    private final Long id;
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final Integer quantity;
    private final Long totalPrice;
    private final OrderStatus status;
    private final LocalDateTime createdAt;
    private final String createdBy;

    public CreateOrderResponseDto(Long id, String orderNumber, String customerName, String productName, Integer quantity, Long totalPrice, OrderStatus status, LocalDateTime createdAt, String createdBy) {
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