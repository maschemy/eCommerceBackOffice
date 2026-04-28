package com.ecommercebackoffice.dashboard.service;

import com.ecommercebackoffice.admin.enums.AdminStatus;
import com.ecommercebackoffice.admin.repository.AdminRepository;
import com.ecommercebackoffice.customer.entity.CustomerStatus;
import com.ecommercebackoffice.customer.repository.CustomerRepository;
import com.ecommercebackoffice.dashboard.dto.DashboardResponseDto;
import com.ecommercebackoffice.dashboard.dto.DashboardSummaryDto;
import com.ecommercebackoffice.dashboard.dto.DashboradWidgetsDto;
import com.ecommercebackoffice.order.entity.OrderStatus;
import com.ecommercebackoffice.order.repository.OrderRepository;
import com.ecommercebackoffice.product.entity.ProductStatus;
import com.ecommercebackoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public DashboardResponseDto getDashboard(){
        return new DashboardResponseDto(
                getSummary(),
                getWidget()
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
                orderRepository.countTodayOrders()
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


}
