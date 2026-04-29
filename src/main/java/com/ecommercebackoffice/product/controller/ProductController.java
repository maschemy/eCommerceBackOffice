package com.ecommercebackoffice.product.controller;

import com.ecommercebackoffice.admin.entity.Admin;
import com.ecommercebackoffice.admin.repository.AdminRepository;
import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.common.Const;
import com.ecommercebackoffice.product.dto.*;
import com.ecommercebackoffice.product.entity.ProductCategory;
import com.ecommercebackoffice.product.entity.ProductStatus;
import com.ecommercebackoffice.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final AdminRepository adminRepository;

    // 상품 등록 API
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<Void> createProduct(
            @Valid @RequestBody CreateProductRequestDto request,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        Admin admin = adminRepository.findById(loginAdmin.adminId())
                .orElseThrow(() -> new IllegalStateException("관리자를 찾을 수 없습니다."));
        productService.createProduct(request, admin);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 상품 리스트 조회 API
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<PageResponseDto<ProductListResponseDto>> getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        PageResponseDto<ProductListResponseDto> result = productService.getProducts(
                keyword, category, status, page, size, sortBy, sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 상품 상세 조회 API
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<ProductDetailResponseDto> getProduct(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin
    ) {
        ProductDetailResponseDto result = productService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 상품 정보 수정 API
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequestDto request,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        productService.updateProduct(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 재고 변경 API
    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<Void> changeStock(
            @PathVariable Long id,
            @Valid @RequestBody ChangeStockRequestDto request,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        productService.changeStock(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 상태 변경 API
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<Void> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody ChangeStatusRequestDto request,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        productService.changeStatus(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 상품 삭제 API
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
