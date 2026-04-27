package com.ecommercebackoffice.order.dto;

import com.ecommercebackoffice.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class UpdateOrderRequestDto {
    private OrderStatus status;
}
