package com.ecommercebackoffice.product.dto;

import com.ecommercebackoffice.product.entity.Product;
import com.ecommercebackoffice.product.entity.ProductCategory;
import com.ecommercebackoffice.product.entity.ProductStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductListResponseDto {
    // 목록 조회 시 필요한 상품 정보 포함
    private final Long id;
    private final String name;
    private final ProductCategory category;
    private final Integer price;
    private final Integer stock;
    private final ProductStatus status;
    private final LocalDateTime createdAt;

    public ProductListResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
        this.createdAt = product.getCreatedAt();
    }
}
