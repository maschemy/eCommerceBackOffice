package com.ecommercebackoffice.product.dto;

import com.ecommercebackoffice.product.entity.Product;
import com.ecommercebackoffice.product.entity.ProductCategory;
import com.ecommercebackoffice.product.entity.ProductStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductDetailResponseDto {
    // 상세 조회 시 관리자 정보를 포함해서 반환
    private final Long id;
    private final String name;
    private final ProductCategory category;
    private final Integer price;
    private final Integer stock;
    private final ProductStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Long adminId;
    private final String adminName;
    private final String adminEmail;

    public ProductDetailResponseDto(Product product, String adminName, String adminEmail) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
        this.createdAt = product.getCreatedAt();
        this.modifiedAt = product.getModifiedAt();
        this.adminId = product.getAdminId();
        this.adminName = adminName;
        this.adminEmail = adminEmail;
    }
}
