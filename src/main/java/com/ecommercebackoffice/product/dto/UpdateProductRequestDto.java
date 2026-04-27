package com.ecommercebackoffice.product.dto;

import com.ecommercebackoffice.product.entity.ProductCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateProductRequestDto {
    // 수정 가능한 필드만 포함
    @NotBlank(message = "상품명은 필수입니다.")
    @Size(max = 100, message = "상품명은 100자 이하 입니다.")
    private String name;

    @NotBlank(message = "카테고리명은 필수입니다.")
    private ProductCategory category;

    @NotBlank(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private Integer price;
}
