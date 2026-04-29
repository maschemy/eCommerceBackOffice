package com.ecommercebackoffice.dashboard.dto;

import lombok.Getter;

// 별점별 리뷰 개수를 세기 위한 dto
@Getter
public class ReviewRatingDistributionDto {
    private final long rating;
    private final long count;

    public ReviewRatingDistributionDto(long rating, long count) {
        this.rating = rating;
        this.count = count;
    }
}
