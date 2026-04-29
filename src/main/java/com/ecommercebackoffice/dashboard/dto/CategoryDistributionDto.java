package com.ecommercebackoffice.dashboard.dto;

import com.ecommercebackoffice.product.entity.ProductCategory;
import lombok.Getter;

@Getter
public class CategoryDistributionDto {
    private final ProductCategory productCategory;
    private final long count;

    public CategoryDistributionDto(ProductCategory productCategory, long count) {
        this.productCategory = productCategory;
        this.count = count;
    }
}
