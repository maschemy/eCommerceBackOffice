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

    /**
     * Summary 통계 출력
     * 전체 관리자 수, 활성 관리자 수
     * 전체 고객 수, 활성 고객 수
     * 전체 상품 수, 재고 부족 상품 수: 재고 5개 이하
     * 전체 주문 수, 오늘 주문 수
     * 전체 리뷰 수, 평균 평점
     * @return
     */
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

    /**
     * Widgets 데이터 출력
     * 총 매출, 오늘 매출
     * 준비중 주문 수
     * 배송중 주문 수
     * 배송완료 주문 수
     * 재고 부족 상품 수: 재고 5개 이하
     * 재고 없음(품절) 상품 수
     * @return
     */
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

    /**
     * Chart 데이터 출력
     * 리뷰 평점 분포: 별점별 개수
     * 고객 상태 분포: 상태별 고객 수
     * 상품 카테고리 분포: 카테고리별 상품 수
     * @return
     */
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

    /**
     * 최근 주문 목록 출력
     * 최근 주문 10개 조회
     * 각 주문의 주요 정보(주문번호, 고객명, 상품명, 금액, 상태) 포함
     * @return
     */
    private List<DashboardRecentOrderDto> getRecentOrder(){
        return orderRepository.findRecentOrders(PageRequest.of(0,10))
                .stream()
                .map(DashboardRecentOrderDto::new)
                .toList();
    }

}
