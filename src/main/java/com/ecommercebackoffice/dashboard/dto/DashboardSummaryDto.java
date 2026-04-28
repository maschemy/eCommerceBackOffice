package com.ecommercebackoffice.dashboard.dto;

import lombok.Getter;

@Getter
public class DashboardSummaryDto {
    private final long totalAdmin;
    private final long activeAdmin;
    private final long totalCustomers;
    private final long activeCustomers;
    private final long totalProducts;
    private final long lowStockProducts; // 5개 이하 재고를 가진 상품 수
    private final long totalOrders;
    private final long todayOrders;

    public DashboardSummaryDto(long totalAdmin, long activeAdmin, long totalCustomers, long activeCustomers, long totalProducts, long lowStockProducts, long totalOrders, long todayOrders) {
        this.totalAdmin = totalAdmin;
        this.activeAdmin = activeAdmin;
        this.totalCustomers = totalCustomers;
        this.activeCustomers = activeCustomers;
        this.totalProducts = totalProducts;
        this.lowStockProducts = lowStockProducts;
        this.totalOrders = totalOrders;
        this.todayOrders = todayOrders;
    }
}
