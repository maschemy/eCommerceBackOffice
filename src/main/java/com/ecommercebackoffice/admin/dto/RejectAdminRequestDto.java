package com.ecommercebackoffice.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RejectAdminRequestDto {

    @NotBlank(message = "거부 사유를 입력해주세요.")
    private String reject;
}
