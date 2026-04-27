package com.ecommercebackoffice.order.dto;

import com.ecommercebackoffice.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateOrderResponseDto {
    private final Long id;
    private final String orderNumber;
    private final OrderStatus status;
    private final LocalDateTime modifiedAt;

    public UpdateOrderResponseDto(Long id, String orderNumber, OrderStatus status, LocalDateTime modifiedAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.status = status;
        this.modifiedAt = modifiedAt;
    }
}
