package com.ecommercebackoffice.review.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LatestReview {
    private final String customerName;
    private final Integer rating;
    private final String content;
    private final LocalDateTime createdAt;

    public LatestReview(String customerName, Integer rating, String content, LocalDateTime createdAt) {
        this.customerName = customerName;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
    }
}
