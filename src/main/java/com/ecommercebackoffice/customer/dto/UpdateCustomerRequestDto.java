package com.ecommercebackoffice.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateCustomerRequestDto {

    private String name;
    @Email
    private String email;

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String phoneNumber;
}
