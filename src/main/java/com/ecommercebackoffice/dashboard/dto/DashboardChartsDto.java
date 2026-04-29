package com.ecommercebackoffice.dashboard.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DashboardChartsDto {
    List<ReviewRatingDistributionDto> reviewRatingDistribution;
    List<CustomerStatusDistributionDto> customerStatusDistribution;
    List<CategoryDistributionDto> categoryDistribution;

    public DashboardChartsDto(List<ReviewRatingDistributionDto> reviewRatingDistribution,
                              List<CustomerStatusDistributionDto> customerStatusDistribution,
                              List<CategoryDistributionDto> categoryDistribution) {
        this.reviewRatingDistribution = reviewRatingDistribution;
        this.customerStatusDistribution = customerStatusDistribution;
        this.categoryDistribution = categoryDistribution;
    }
}
