package com.ecommercebackoffice.product.controller;

import com.ecommercebackoffice.product.dto.CreateProductRequestDto;
import com.ecommercebackoffice.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // http 세션
    private Long getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER_ID") == null)
            throw new IllegalArgumentException("로그인이 필요합니다.");
        return (Long) session.getAttribute("LOGIN_USER_ID");
    }

    // 상품 등록 API
    @PostMapping
    public ResponseEntity<Void> createProduct(
            @Valid @RequestBody CreateProductRequestDto request, HttpServletRequest httpRequest
    ) {
        // 세션에서 관리자 id 추출
        Long adminId = getUserIdFromSession(httpRequest);

        // 추출한 관리자 id를 service로 전달
        Long productId = productService.createProduct(request, adminId);
        return ResponseEntity.created(URI.create("/api/products/" + productId)).build();
    }
}
