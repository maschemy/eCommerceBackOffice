package com.ecommercebackoffice.customer.dto;

import com.ecommercebackoffice.customer.entity.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCustomerStatusResponseDto {
    private final Long id;
    private final CustomerStatus status;
    private final LocalDateTime modifiedAt;

    public UpdateCustomerStatusResponseDto(Long id, CustomerStatus status, LocalDateTime modifiedAt) {
        this.id = id;
        this.status = status;
        this.modifiedAt = modifiedAt;
    }
}
