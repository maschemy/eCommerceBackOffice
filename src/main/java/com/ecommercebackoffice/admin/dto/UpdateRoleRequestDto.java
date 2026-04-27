package com.ecommercebackoffice.admin.dto;

import com.ecommercebackoffice.admin.enums.AdminRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateRoleRequestDto {

    @NotNull
    private AdminRole role;
}
