package com.ecommercebackoffice.admin.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateMyInfoResponseDto {
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final LocalDateTime modifiedAt;

    public UpdateMyInfoResponseDto(String name, String email, String phoneNumber, LocalDateTime modifiedAt) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.modifiedAt = modifiedAt;
    }
}
