package com.ecommercebackoffice.admin.dto;

import com.ecommercebackoffice.admin.enums.AdminRole;
import lombok.Getter;

@Getter
public class UpdateRoleRequestDto {

    private AdminRole role;
}
