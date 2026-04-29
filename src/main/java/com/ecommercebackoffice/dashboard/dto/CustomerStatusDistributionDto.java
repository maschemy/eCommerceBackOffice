package com.ecommercebackoffice.dashboard.dto;

import com.ecommercebackoffice.customer.entity.CustomerStatus;
import lombok.Getter;

@Getter
public class CustomerStatusDistributionDto {
    private final CustomerStatus customerStatus;
    private final long count;

    public CustomerStatusDistributionDto(CustomerStatus customerStatus, long count) {
        this.customerStatus = customerStatus;
        this.count = count;
    }
}
