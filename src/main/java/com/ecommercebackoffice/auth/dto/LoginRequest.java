package com.ecommercebackoffice.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Email
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank
    @Size(min=8 , message= "비밀번호는 8자 이상이여야합니다")
    private String password;
}
