package com.ecommercebackoffice.admin.dto;

import com.ecommercebackoffice.admin.enums.AdminRole;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class CreateAdminRequestDto {

    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8)
    private String password;
    @NotBlank(message = "휴대폰번호를 입력해주세요")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String phoneNumber;
    //enum 클래스는 @NotNull 사용
    @NotNull(message = "관리자 역할은 필수입니다.")
    private AdminRole role;

}
