package com.ecommercebackoffice.entity;

// 판매중, 품절, 단종 상태를 정의
public enum ProductStatus {
    ON_SALE("판매중"),
    OUT_OF_STOCK("품절"),
    DISCOUNTED("단종");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}