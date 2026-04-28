package com.ecommercebackoffice.review.service;

import com.ecommercebackoffice.product.dto.PageResponseDto;
import com.ecommercebackoffice.review.dto.ReviewDetailResponseDto;
import com.ecommercebackoffice.review.dto.ReviewListResponseDto;
import com.ecommercebackoffice.review.entity.Review;
import com.ecommercebackoffice.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    // 리뷰 리스트 조회
    public PageResponseDto<ReviewListResponseDto> getReviews(
            String keyword,
            Integer rating,
            int page,
            int size,
            String sortBy,
            String sortOrder
    ) {
        // 페이지 번호는 내부적으로 0부터 시작하므로 1을 빼준다
        int pageNumber = Math.max(page - 1, 0);
        int pageSize = size > 0 ? size : 10;

        // 정렬 조건 생성
        Sort sort = createSort(sortBy, sortOrder);
        Pageable pagealble = PageRequest.of(pageNumber, pageSize, sort);

        // 조건에 맞는 리뷰 조회
        Page<Review> reviewPage = reviewRepository.findByConditions(keyword, rating, pagealble);

        // Review 엔티티를 ReviewListResponseDto로 변환
        List<ReviewListResponseDto> responseList = new ArrayList<>();
        for (Review review : reviewPage.getContent()) {
            responseList.add(new ReviewListResponseDto(review));
        }

        Page<ReviewListResponseDto> responsePage = new PageImpl<>(
                responseList,
                pagealble,
                reviewPage.getTotalElements()
        );
        return new PageResponseDto<>(responsePage);
    }

    // 리뷰 상세 조회
    public ReviewDetailResponseDto getReview(Long id) {
        Review review = findReviewById(id);
        return new ReviewDetailResponseDto(review);
    }

    // 정렬 조건 생성 메서드
    private Sort createSort(String sortBy, String sortOrder) {
    }

    // 리뷰 조회 메서드
    private Review findReviewById(Long id) {
    }
}
