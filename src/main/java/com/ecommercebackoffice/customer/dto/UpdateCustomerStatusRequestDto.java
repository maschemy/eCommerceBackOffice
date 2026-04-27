package com.ecommercebackoffice.customer.dto;

import com.ecommercebackoffice.customer.entity.CustomerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateCustomerStatusRequestDto {

    @NotNull(message = "상태값을 입력해 주세요.")
    private CustomerStatus status;
}
