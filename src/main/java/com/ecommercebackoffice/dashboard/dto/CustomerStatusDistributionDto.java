package com.ecommercebackoffice.dashboard.dto;

import com.ecommercebackoffice.customer.entity.CustomerStatus;
import lombok.Getter;

// 상태별 고객 수를 세기 위한 dto
@Getter
public class CustomerStatusDistributionDto {
    private final CustomerStatus customerStatus;
    private final long count;

    public CustomerStatusDistributionDto(CustomerStatus customerStatus, long count) {
        this.customerStatus = customerStatus;
        this.count = count;
    }
}
