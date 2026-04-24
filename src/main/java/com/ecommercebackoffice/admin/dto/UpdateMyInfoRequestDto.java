package com.ecommercebackoffice.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateMyInfoRequestDto {

    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식에 맞게 입력해주세요")
    private String email;
    @NotBlank(message = "휴대폰번호를 입력해주세요")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String phoneNumber;
}
