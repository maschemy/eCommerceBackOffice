package com.ecommercebackoffice.review.controller;

import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.product.dto.PageResponseDto;
import com.ecommercebackoffice.review.dto.ReviewDetailResponseDto;
import com.ecommercebackoffice.review.dto.ReviewListResponseDto;
import com.ecommercebackoffice.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 리스트 조회 API
    @GetMapping
    public ResponseEntity<PageResponseDto<ReviewListResponseDto>> getReviews(
            @RequestParam(required = false) String keyword, // 고객명, 상품명
            @RequestParam(required = false) Integer rating, // 평점 필터
            @RequestParam(defaultValue = "1") int page, // 페이지 번호
            @RequestParam(defaultValue = "10") int size, // 페이지당 개수
            @RequestParam(defaultValue = "createdAt") String sortBy, // 정렬 기준
            @RequestParam(defaultValue = "desc") String sortOder, // 정렬 방향
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        PageResponseDto<ReviewListResponseDto> result = reviewService.getReviews(
                keyword, rating, page, size ,sortBy, sortOder);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 리뷰 상세 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDetailResponseDto> getReview(
            @PathVariable Long id,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        ReviewDetailResponseDto result = reviewService.getReview(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 리뷰 삭제 API
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long id,
            @AuthenticationPrincipal LoginAdmin loginAdmin
    ) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
