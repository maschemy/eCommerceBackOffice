package com.ecommercebackoffice.product.dto;

import com.ecommercebackoffice.product.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangeStatusRequestDto {
    // 변경할 상품 상태를 입력받는다
    @NotBlank(message = "상태는 필수입니다.")
    private ProductStatus status;
}
