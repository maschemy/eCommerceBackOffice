package com.ecommercebackoffice.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangeStockRequestDto {
    // 재고 증감량을 입력받는다
    @NotBlank(message = "재고 변경량은 필수입니다.")
    private Integer amount;
}
