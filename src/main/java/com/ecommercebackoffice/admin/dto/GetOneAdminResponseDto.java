package com.ecommercebackoffice.admin.dto;

import com.ecommercebackoffice.admin.enums.AdminRole;
import com.ecommercebackoffice.admin.enums.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneAdminResponseDto {

    private final String name;
    private final String email;
    private final String phoneNumber;
    private final AdminRole role;
    private final AdminStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetOneAdminResponseDto(String name, String email, String phoneNumber, AdminRole role, AdminStatus status, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
