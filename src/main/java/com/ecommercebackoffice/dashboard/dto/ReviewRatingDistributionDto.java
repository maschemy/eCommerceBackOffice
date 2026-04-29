package com.ecommercebackoffice.dashboard.dto;

import lombok.Getter;

@Getter
public class ReviewRatingDistributionDto {
    private final long rating;
    private final long count;

    public ReviewRatingDistributionDto(long rating, long count) {
        this.rating = rating;
        this.count = count;
    }
}
