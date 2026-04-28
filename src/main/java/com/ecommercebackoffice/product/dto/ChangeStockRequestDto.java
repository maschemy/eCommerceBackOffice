package com.ecommercebackoffice.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangeStockRequestDto {
    // 재고 증감량을 입력받는다
    @NotNull(message = "재고 변경량은 필수입니다.")
    private Integer amount;
}
