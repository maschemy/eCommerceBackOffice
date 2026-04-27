package com.ecommercebackoffice.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CancelOrderRequestDto {
    @NotBlank(message = "취소 사유를 반드시 입력해주세요")
    private String cancelReason;
}
