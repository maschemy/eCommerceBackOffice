package com.ecommercebackoffice.entity;

// 전자기기, 패션/의류, 식품 카테고리를 정의
public enum ProductCategory {
    ELECTRONICS("전자기기"),
    FASHION("패션/의류"),
    FOOD("식품");

    private final String description;

    // 생성자
    ProductCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}