package com.ecommercebackoffice.dashboard.dto;

import com.ecommercebackoffice.product.entity.ProductCategory;
import lombok.Getter;

// 카테고리별 상품 수를 세기 위한 dto
@Getter
public class CategoryDistributionDto {
    private final ProductCategory productCategory;
    private final long count;

    public CategoryDistributionDto(ProductCategory productCategory, long count) {
        this.productCategory = productCategory;
        this.count = count;
    }
}
