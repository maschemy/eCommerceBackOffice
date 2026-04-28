package com.ecommercebackoffice.review.dto;

import com.ecommercebackoffice.review.entity.Review;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReviewDetailResponseDto {
    private final String productName;
    private final String customerName;
    private final String customerEmail;
    private final Integer rating;
    private final String content;
    private final LocalDateTime createdAt;

    public ReviewDetailResponseDto(Review review) {
        this.productName = review.getOrder().getProduct().getName();
        this.customerName = review.getOrder().getCustomer().getName();
        this.customerEmail = review.getOrder().getCustomer().getEmail();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}
