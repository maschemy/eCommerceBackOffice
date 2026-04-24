package com.ecommercebackoffice.order.dto;

import lombok.Getter;

@Getter
public class CreateOrderRequestDto {
    private Long customerId;
    private Long productId;
    private int quantity;
}
