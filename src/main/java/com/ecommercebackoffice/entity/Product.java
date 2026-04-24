package com.ecommercebackoffice.entity;

import com.ecommercebackoffice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// BaseEntity를 상속받아 사용

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private ProductCategory category;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private ProductStatus status;

    @Column(nullable = false)
    private Long adminId;

    public Product(String name, ProductCategory category, Integer price,
                   Integer stock, ProductStatus status, Long adminId) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.adminId = adminId;
    }

    // 상품명, 카테고리, 가격만 수정 가능하도록 제한
    public void updateInfo(String name, ProductCategory category, Integer price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }



}
