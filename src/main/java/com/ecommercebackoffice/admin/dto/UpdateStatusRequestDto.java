package com.ecommercebackoffice.admin.dto;

import com.ecommercebackoffice.admin.enums.AdminStatus;
import lombok.Getter;

@Getter
public class UpdateStatusRequestDto {

    private AdminStatus status;
}
