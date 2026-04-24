package com.ecommercebackoffice.product.entity;

import com.ecommercebackoffice.common.entity.BaseEntity;
import jakarta.persistence.*;
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

    // Enum을 사용하고 이름 문자열을 사용하므로 String을 사용해준다.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @Enumerated(EnumType.STRING)
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

    // 재고 변경 시 단종 상태가 아니면 자동으로 상태를 전환한다
    public void changeStock(int amount) {
        this.stock = stock + amount;

        if (this.status == ProductStatus.DISCOUNTED) {
            return;
        }

        if (this.stock <= 0) {
            this.stock = 0;
            this.status = ProductStatus.OUT_OF_STOCK;
        } else {
            this.status = ProductStatus.ON_SALE;
        }
    }

    // 관리자가 수동으로 상품 상태 변경
    public void changeStatus(ProductStatus status) {
        this.status = status;
    }
}
