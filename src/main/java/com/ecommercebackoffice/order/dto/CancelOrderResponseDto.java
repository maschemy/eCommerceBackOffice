package com.ecommercebackoffice.order.dto;

import com.ecommercebackoffice.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CancelOrderResponseDto {
    private final Long id;
    private final String orderNumber;
    private final OrderStatus status;
    private final String cancelReason;
    private final LocalDateTime modifiedAt;

    public CancelOrderResponseDto(Long id, String orderNumber, OrderStatus status, String cancelReason, LocalDateTime modifiedAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.status = status;
        this.cancelReason = cancelReason;
        this.modifiedAt = modifiedAt;
    }
}
