package com.ecommercebackoffice.customer.dto;

import com.ecommercebackoffice.customer.entity.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SearchCustomerResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final CustomerStatus customerStatus;
    private final LocalDateTime createdAt;
    private final Long totalOrderCount;
    private final Long totalOrderAmount;

    public SearchCustomerResponseDto(
            Long id,String name, String email, String phoneNumber, CustomerStatus customerStatus, LocalDateTime createdAt, Long totalOrderCount, Long totalOrderAmount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.customerStatus = customerStatus;
        this.createdAt = createdAt;
        this.totalOrderCount = totalOrderCount;
        this.totalOrderAmount = totalOrderAmount;
    }


}
