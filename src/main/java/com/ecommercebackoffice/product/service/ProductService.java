package com.ecommercebackoffice.product.service;

import com.ecommercebackoffice.product.dto.CreateProductRequestDto;
import com.ecommercebackoffice.product.entity.Product;
import com.ecommercebackoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 입력 받은 정보로 새로운 상품 등록
    @Transactional
    public Long createProduct(CreateProductRequestDto request) {
        Product product = new Product(
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getStock(),
                request.getStatus(),
                request.getAdminId()
        );

        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }
}
