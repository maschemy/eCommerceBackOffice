package com.ecommercebackoffice.order.dto;

import com.ecommercebackoffice.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateOrderRequestDto {
    @NotNull(message = "주문 상태를 입력해주세요")
    private OrderStatus status;
}
