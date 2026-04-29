package com.ecommercebackoffice.dashboard.service;

import com.ecommercebackoffice.admin.enums.AdminStatus;
import com.ecommercebackoffice.admin.repository.AdminRepository;
import com.ecommercebackoffice.customer.entity.CustomerStatus;
import com.ecommercebackoffice.customer.repository.CustomerRepository;
import com.ecommercebackoffice.dashboard.dto.*;
import com.ecommercebackoffice.order.entity.OrderStatus;
import com.ecommercebackoffice.order.repository.OrderRepository;
import com.ecommercebackoffice.product.entity.ProductCategory;
import com.ecommercebackoffice.product.entity.ProductStatus;
import com.ecommercebackoffice.product.repository.ProductRepository;
import com.ecommercebackoffice.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public DashboardResponseDto getDashboard(){
        return new DashboardResponseDto(
                getSummary(),
                getWidget(),
                getCharts(),
                getRecentOrder()
        );
    }

    private DashboardSummaryDto getSummary(){
        return new DashboardSummaryDto(
                adminRepository.countByDeletedAtIsNull(),
                adminRepository.countByStatusAndDeletedAtIsNull(AdminStatus.ACTIVE),
                customerRepository.countByDeletedAtIsNull(),
                customerRepository.countByStatusAndDeletedAtIsNull(CustomerStatus.ACTIVE),
                productRepository.count(),
                productRepository.countByStockLessThanEqual(5),
                orderRepository.count(),
                orderRepository.countTodayOrders(),
                reviewRepository.countBydeletedAtIsNull(),
                reviewRepository.findAverageRating()
        );
    }

    private DashboradWidgetsDto getWidget(){
        return new DashboradWidgetsDto(
                orderRepository.sumTotalRevenue(OrderStatus.CANCEL),
                orderRepository.sumTodayRevenue(OrderStatus.CANCEL),
                orderRepository.countStatusOrders(OrderStatus.READY),
                orderRepository.countStatusOrders(OrderStatus.DELIVERY),
                orderRepository.countStatusOrders(OrderStatus.COMPLETE),
                productRepository.countByStockLessThanEqual(5),
                productRepository.countByStatus(ProductStatus.OUT_OF_STOCK)
        );
    }

    private DashboardChartsDto getCharts(){
        // 리뷰 평점 분포
        List<Object[]> ratingData = reviewRepository.countByRating();
        List<ReviewRatingDistributionDto> ratingDistribution = ratingData.stream()
                .map(row -> new ReviewRatingDistributionDto(
                        ((Number) row[0]).longValue(),
                        ((Number) row[1]).longValue()
                )).toList();

        // 상태별 고객 수
        List<Object[]> customerData = customerRepository.countByStatus();
        List<CustomerStatusDistributionDto> customerStatusDistribution = customerData.stream()
                .map(row -> new CustomerStatusDistributionDto(
                        ((CustomerStatus) row[0]),
                        ((Number) row[1]).longValue()
                )).toList();

        // 카테고리별 상품 수
        List<Object[]> categoryData = productRepository.countByCategory();
        List<CategoryDistributionDto> categoryDistribution = categoryData.stream()
                .map(row -> new CategoryDistributionDto(
                        ((ProductCategory)row[0]),
                        ((Number) row[1]).longValue()
                )).toList();

        return new DashboardChartsDto(ratingDistribution, customerStatusDistribution, categoryDistribution);
    }

    private List<DashboardRecentOrderDto> getRecentOrder(){
        return orderRepository.findRecentOrders(PageRequest.of(0,10))
                .stream()
                .map(DashboardRecentOrderDto::new)
                .toList();
    }

}
