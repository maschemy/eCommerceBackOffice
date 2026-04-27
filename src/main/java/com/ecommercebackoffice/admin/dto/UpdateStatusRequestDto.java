package com.ecommercebackoffice.admin.dto;

import com.ecommercebackoffice.admin.enums.AdminStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateStatusRequestDto {

    @NotNull
    private AdminStatus status;
}
