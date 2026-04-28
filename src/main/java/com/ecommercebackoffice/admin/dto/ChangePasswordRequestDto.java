package com.ecommercebackoffice.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ChangePasswordRequestDto {

    @NotBlank(message = "기존비밀번호를 입력해주세요")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String currentPassword; // 기존 비밀번호
    @NotBlank(message = "새로운 비밀번호를 입력해주세요")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String newPassword;     // 새 비밀번호
}
