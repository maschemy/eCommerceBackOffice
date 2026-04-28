package com.ecommercebackoffice.review.dto;

import com.ecommercebackoffice.review.entity.Review;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReviewListResponseDto {
    private final Long id;
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final Integer rating;
    private final String content;
    private final LocalDateTime createdAt;

    // 엔티티를 Dto로 변환
    public ReviewListResponseDto(Review review) {
        this.id = review.getId();
        // review -> order -> orderNumber
        this.orderNumber = review.getOrder().getOrderNumber();
        this.customerName = review.getOrder().getCustomer().getName();
        this.productName = review.getOrder().getProduct().getName();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}
