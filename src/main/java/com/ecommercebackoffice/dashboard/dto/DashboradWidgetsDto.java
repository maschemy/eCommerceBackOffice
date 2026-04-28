package com.ecommercebackoffice.dashboard.dto;

import lombok.Getter;

@Getter
public class DashboradWidgetsDto {
    private final long totalRevenue;    // 총 매출
    private final long todayTotalRevenue;   // 오늘 매출
    private final long preparingOrders;
    private final long deliveryOrders;
    private final long completeOrders;
    private final long lowStockProduct; //재고 부족 상품(5개 이하)
    private final long lessStockProduct; // 재고 없음(품절) 상품 수

    public DashboradWidgetsDto(long totalRevenue, long todayTotalRevenue, long preparingOrders, long deliveryOrders, long completeOrders, long lowStockProduct, long lessStockProduct) {
        this.totalRevenue = totalRevenue;
        this.todayTotalRevenue = todayTotalRevenue;
        this.preparingOrders = preparingOrders;
        this.deliveryOrders = deliveryOrders;
        this.completeOrders = completeOrders;
        this.lowStockProduct = lowStockProduct;
        this.lessStockProduct = lessStockProduct;
    }
}
