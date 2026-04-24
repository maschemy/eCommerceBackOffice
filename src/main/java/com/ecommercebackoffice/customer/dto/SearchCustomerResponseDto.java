package com.ecommercebackoffice.customer.dto;

import com.ecommercebackoffice.customer.entity.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SearchCustomerResponseDto {
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final CustomerStatus customerStatus;
    private final LocalDateTime createdAt;

    public SearchCustomerResponseDto(String name, String email, String phoneNumber, CustomerStatus customerStatus, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.customerStatus = customerStatus;
        this.createdAt = createdAt;
    }

}
