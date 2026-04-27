package com.ecommercebackoffice.admin.dto;

import com.ecommercebackoffice.admin.enums.AdminStatus;
import lombok.Getter;

@Getter
public class UpdateStatusResponseDto {

    private final AdminStatus status;

    public UpdateStatusResponseDto(AdminStatus status) {
        this.status = status;
    }
}
