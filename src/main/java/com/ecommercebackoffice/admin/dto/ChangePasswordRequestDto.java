package com.ecommercebackoffice.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangePasswordRequestDto {

    @NotBlank
    @Min(8)
    private String currentPassword; // 기존 비밀번호
    @NotBlank
    @Min(8)
    private String newPassword;     // 새 비밀번호
}
