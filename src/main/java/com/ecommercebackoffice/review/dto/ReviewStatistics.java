package com.ecommercebackoffice.review.dto;

import lombok.Getter;

@Getter
public class ReviewStatistics {
    private final Double ratingAverage;
    private final Long totalReview;
    private final Long fiveRatings;
    private final Long fourRatings;
    private final Long threeRatings;
    private final Long twoRatings;
    private final Long oneRatings;

    public ReviewStatistics(Double ratingAverage, Long totalReview, Long fiveRatings, Long fourRatings, Long threeRatings, Long twoRatings, Long oneRatings) {
        this.ratingAverage = Math.round(ratingAverage * 10.0) / 10.0;
        this.totalReview = totalReview;
        this.fiveRatings = fiveRatings;
        this.fourRatings = fourRatings;
        this.threeRatings = threeRatings;
        this.twoRatings = twoRatings;
        this.oneRatings = oneRatings;
    }
}
