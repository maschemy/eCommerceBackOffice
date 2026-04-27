package com.ecommercebackoffice.admin.dto;

import lombok.Getter;

@Getter
public class GetMyInfoResponseDto {
    private final String name;
    private final String email;
    private final String phoneNumber;

    public GetMyInfoResponseDto(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
