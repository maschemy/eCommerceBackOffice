package com.ecommercebackoffice.product.controller;

import com.ecommercebackoffice.product.dto.*;
import com.ecommercebackoffice.product.entity.ProductCategory;
import com.ecommercebackoffice.product.entity.ProductStatus;
import com.ecommercebackoffice.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // http 세션
    private Long getAdminIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_Admin_ID") == null)
            throw new IllegalArgumentException("로그인이 필요합니다.");
        return (Long) session.getAttribute("LOGIN_Admin_ID");
    }

    // 상품 등록 API
    @PostMapping
    public ResponseEntity<Void> createProduct(
            @Valid @RequestBody CreateProductRequestDto request, HttpServletRequest httpRequest
    ) {
        // 세션에서 관리자 id 추출
        Long adminId = getAdminIdFromSession(httpRequest);

        // 추출한 관리자 id를 service로 전달
        Long productId = productService.createProduct(request, adminId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 상품 리스트 조회 API
    @GetMapping
    public ResponseEntity<PageResponseDto<ProductListResponseDto>> getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false)ProductStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            HttpServletRequest httpRequest
    ) {
        getAdminIdFromSession(httpRequest);

        PageResponseDto<ProductListResponseDto> response = productService.getProducts(
                keyword, category, status, page, size, sortBy, sortOrder);
        return ResponseEntity.ok(response);
    }

    // 상품 상세 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponseDto> getProduct(
            @PathVariable Long id,
            HttpServletRequest httpRequest
    ) {
        getAdminIdFromSession(httpRequest);

        ProductDetailResponseDto response = productService.getProduct(id);
        return ResponseEntity.ok(response);
    }

    // 상품 정보 수정 API
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequestDto request,
            HttpServletRequest httpRequest
    ) {
        getAdminIdFromSession(httpRequest);

        productService.updateProduct(id, request);
        return ResponseEntity.ok().build();
    }

    // 재고 변경 API
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> changeStock(
            @PathVariable Long id,
            @Valid @RequestBody ChangeStockRequestDto request,
            HttpServletRequest httpRequest
    ) {
        getAdminIdFromSession(httpRequest);

        productService.changeStock(id, request);
        return ResponseEntity.ok().build();
    }
}
