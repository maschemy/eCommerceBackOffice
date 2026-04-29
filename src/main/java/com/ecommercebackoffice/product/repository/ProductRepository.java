package com.ecommercebackoffice.product.repository;

import com.ecommercebackoffice.product.entity.Product;
import com.ecommercebackoffice.product.entity.ProductCategory;
import com.ecommercebackoffice.product.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JPA를 활용한 상품 데이터 접근 계층을 구현
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 키워드, 카테고리, 상태 조건에 따라 상품을 조회
    @Query("SELECT p FROM Product p WHERE " +
           "(:keyword IS NULL OR p.name LIKE %:keyword%) AND " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:status IS NULL OR p.status = :status)")
    Page<Product> findByConditions(
            @Param("keyword") String keyword,
            @Param("category")ProductCategory category,
            @Param("status") ProductStatus status,
            Pageable pageable
    );

    // 상품 상태별 개수
    long countByStatus(ProductStatus status);

    // 특정 재고 이하 상품 출력
    long countByStockLessThanEqual(int stock);


    // 카테고리별 상품 수
    @Query("SELECT p.category, COUNT(p) FROM Product p GROUP BY p.category")
    List<Object[]> countByCategory();
}
