package com.ecommercebackoffice.admin.dto;

import com.ecommercebackoffice.admin.enums.AdminRole;
import lombok.Getter;

@Getter
public class UpdateRoleResponseDto {

    private final AdminRole role;

    public UpdateRoleResponseDto(AdminRole role) {
        this.role = role;
    }
}
