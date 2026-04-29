package com.ecommercebackoffice.dashboard.dto;

import com.ecommercebackoffice.customer.entity.Customer;
import com.ecommercebackoffice.order.entity.Order;
import com.ecommercebackoffice.product.entity.Product;
import lombok.Getter;

@Getter

public class DashboardRecentOrderDto {
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final Long totalPrice;
    private final String status;

    public DashboardRecentOrderDto(Order order) {
        this.orderNumber = order.getOrderNumber();
        this.customerName = order.getCustomer().getName();
        this.productName = order.getProduct().getName();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus().name();
    }
}
