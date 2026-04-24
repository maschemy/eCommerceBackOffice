package com.ecommercebackoffice.repository;

import com.ecommercebackoffice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA를 활용한 상품 데이터 접근 계층을 구현
public interface ProductRepository extends JpaRepository<Product, Long> {
}
