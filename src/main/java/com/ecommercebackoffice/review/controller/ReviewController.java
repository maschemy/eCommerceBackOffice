package com.ecommercebackoffice.review.controller;

import com.ecommercebackoffice.auth.dto.LoginAdmin;
import com.ecommercebackoffice.common.Const;
import com.ecommercebackoffice.product.dto.PageResponseDto;
import com.ecommercebackoffice.review.dto.ReviewListResponseDto;
import com.ecommercebackoffice.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @SessionAttribute(name = Const.LOGIN_ADMIN) LoginAdmin loginAdmin
    ) {
        PageResponseDto<ReviewListResponseDto> result = reviewService.getReviews(
                keyword, rating, page, size ,sortBy, sortOder);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
