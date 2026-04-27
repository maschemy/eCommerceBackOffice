package com.ecommercebackoffice.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateOrderRequestDto {
    @NotNull(message = "고객id 필수 입력")
    private Long customerId;

    @NotNull(message = "상품id 필수 입력")
    private Long productId;

    @NotNull(message = "수량을 입력해주세요")
    @Min(value = 1, message = "수량은 1이상이어야 합니다")
    private Integer quantity;
}
