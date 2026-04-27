package com.ecommercebackoffice.order.dto;

import com.ecommercebackoffice.admin.enums.AdminRole;
import com.ecommercebackoffice.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReadOneOrderResponseDto {
    private final String orderNumber;
    private final String customerName;
    private final String customerEmail;
    private final String productName;
    private final Integer quantity;
    private final Integer totalPrice;
    private final OrderStatus status;
    private final LocalDateTime createdAt;
    private final String createdByName;
    private final String createdByEmail;
    private final AdminRole createdByRole;

    public ReadOneOrderResponseDto(String orderNumber, String customerName, String customerEmail, String productName, Integer quantity, Integer totalPrice, OrderStatus status, LocalDateTime createdAt, String createdByName, String createdByEmail, AdminRole createdByRole) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.createdByName = createdByName;
        this.createdByEmail = createdByEmail;
        this.createdByRole = createdByRole;
    }
}
